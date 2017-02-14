'use strict';

import Dom from 'metal-dom/src/dom';
import MultiMap from 'metal-structs/src/MultiMap';
import Datatable from 'metal-datatable/src/Datatable';
import Modal from 'metal-modal/src/Modal';
import Ajax from 'metal-ajax/src/Ajax';
import TreeView from 'metal-treeview/src/Treeview';


class FgTable {
  constructor(apiUrl = 'https://localhost/apis/v1.0 ',
      tableIdentifier = '#application', token = '') {
    this.apiUrl = apiUrl;
    this.tableIdentifier = tableIdentifier;
    this.token = token;
  }

  render(resource, columns, detailsCallback, waitElement) {
    if (this.token == null) {
      Dom.toggleClasses(waitElement, 'loaded');
      Dom.append(
          this.tableIdentifier,
          '<h1 class="">No Token available.</h1>'
      );
      return;
    }

    var headers = new MultiMap();
    headers.add('Authorization', 'Bearer ' + this.token);
    headers.add('content-type', 'application/json');

    var resourcesCall = Ajax.request(
        this.apiUrl + '/' + resource,
        'GET', null, headers, null
    );
    var tableBlock = this.tableIdentifier;
    resourcesCall.then(function(data) {
      var tableData = JSON.parse(data.response)[resource];
      tableData.forEach(function(entry) {
        Object.keys(entry).forEach(function(keyEntry) {
          if (columns.indexOf(keyEntry) < 0) {
            delete(entry[keyEntry]);
          }
        });
        columns.forEach(function(keyEntry) {
          if (keyEntry == 'id') {
            entry[keyEntry] = '<a href="#' + entry[keyEntry] +
              '" onClick="' + detailsCallback + '(\'' +
              entry[keyEntry] + '\', \'' + resource + '\')">' +
              entry[keyEntry] + '</a>';
          }
          entry[keyEntry.capitalize()] = entry[keyEntry];
          delete(entry[keyEntry]);
        });
      });
      Dom.toggleClasses(waitElement, 'loaded');
      var dt = new Datatable(
          {
            data: tableData.reverse(),
            displayColumnsType: false,
            formatColumns: unsortColumns,
          },
        tableBlock);
    });
  }

  showDetails(id, resource, deleteCallback) {
    if (this.token.substring(0, 4) == 'User' ||
        this.token.substring(0, 7) == 'No JSON') {
      var modalError = new Modal({
        elementClasses: 'modal-boot',
        header: '<h4 class="modal-title">Error</h4>',
        body: 'No token available!',
      });
      modalError.show();
      return;
    }
    var headers = new MultiMap();
    headers.add('Authorization', 'Bearer ' + this.token);
    headers.add('content-type', 'application/json');

    var resourceDetailsCall = Ajax.request(this.apiUrl + '/' + resource +
        '/' + id, 'GET', null, headers, null);
    resourceDetailsCall.then(function(data) {
      var date = new Date();
      var resourceId = resource + id + date.getTime();
      var modalTask = new Modal({
        elementClasses: 'modal-boot',
        header: '<h4 class="modal-title">' +
          resource.substring(0, resource.length - 1).capitalize() +
          ': ' + id + '</h4>',
        body: '<div id="' + resourceId + '"></div>',
        footer: '<button type="button" onClick="' + deleteCallback +
            '(\'' + id + '\',\'' + resource +
            '\')" class="btn btn-danger">Delete</button>',
      });

      new TreeView({
        nodes: FgTable.convertToNodes(JSON.parse(data.response)),
      }, '#' + resourceId);
      modalTask.show();
    });
  }

  delete(id, resource) {
    var headers = new MultiMap();
    headers.add('Authorization', 'Bearer ' + this.token);
    headers.add('content-type', 'application/json');

    var resourceDetailsCall = Ajax.request(this.apiUrl + '/' + resource +
        '/' + id, 'DELETE', null, headers, null);
    resourceDetailsCall.then(function() {
      window.location.reload();
    });
  }

  static convertToNodes(json) {
    var nodes = new Array();
    var childrenList;
    Object.keys(json).forEach(function(key) {
      if (typeof json[key] !== null && typeof json[key] === 'object') {
        if (Array.isArray(json[key])) {
          childrenList = new Array();
          var arrayElem = 0;
          json[key].forEach(function(childElem) {
            if (typeof childElem !== null &&
                typeof childElem === 'object') {
              childrenList.push({
                name: arrayElem++,
                children: FgTable.convertToNodes(childElem),
              });
            } else {
              childrenList.push({name: childElem});
            }
          });
          if (childrenList.length > 0) {
            nodes.push({name: key, children: childrenList});
          } else {
            nodes.push({name: key, children: [{name: 'N/A'}]});
          }
        } else {
          nodes.push(
              {
                name: key,
                children: FgTable.convertToNodes(json[key]),
              }
          );
        }
      } else {
        nodes.push({name: key, children: [{name: json[key]}]});
      }
    });
    return nodes;
  }
};


function unsortColumns(columns) {
  return columns;
}

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
};


export default FgTable;
