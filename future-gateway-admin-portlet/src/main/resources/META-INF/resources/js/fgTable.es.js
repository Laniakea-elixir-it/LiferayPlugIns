'use strict';

import Dom from 'metal-dom/src/dom';
import MultiMap from 'metal-structs/src/MultiMap';
import Datatable from 'metal-datatable/src/Datatable';
import Modal from 'metal-modal/src/Modal';
import Ajax from 'metal-ajax/src/Ajax';
import TreeView from 'metal-treeview/src/Treeview';
import Pagination from 'metal-pagination/src/Pagination';


class FgTable {
  constructor(apiUrl = 'https://localhost/apis/v1.0 ',
      tableIdentifier = '#application', token = '') {
    this.apiUrl = apiUrl;
    this.tableIdentifier = tableIdentifier;
    this.token = token;
  }

  render(resource, columns, detailsCallback, pageCallback,
      waitElement, page=0, pageSize = 15) {
    this.resource = resource;
    this.columns = columns;
    this.pageCallback = pageCallback;

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
    resourcesCall.then(function(data) {
      var tableData = JSON.parse(data.response)[resource];
      var totalPages = Math.ceil(tableData.length / pageSize);
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
      this.tableData = JSON.stringify(tableData.reverse());
      Dom.addClasses(waitElement, 'loaded');
      Dom.removeChildren(this.tableIdentifier);
      this.dataTable = new Datatable(
          {
            data: tableData.slice(
                pageSize * page,
                pageSize * (page + 1)),
            displayColumnsType: false,
            formatColumns: unsortColumns,
          }, this.tableIdentifier);
      if (totalPages > 1) {
        this.pagination = new Pagination(
            {
              circular: false,
              page: page,
              total: totalPages,
            },
            this.tableIdentifier);
        this.pagination.on(
            Pagination.Events.CHANGE_REQUEST,
            function(event) {
              window[pageCallback](event.state.page);
            }
         );
      }
    }.bind(this));
  }

  update(resource, columns,
      page=0, pageSize = 15) {
    var tableData = JSON.parse(this.tableData);
    var filter = this.filter;
    if (filter) {
      let filteredData = [];
      let boolean
      tableData.forEach(function(entry) {
        if (columns.some(function(keyEntry) {
          if (typeof entry[keyEntry.capitalize()] === 'string' ||
                entry[keyEntry.capitalize()] instanceof String) {
            return entry[keyEntry.capitalize()].includes(filter);
          }
          return false;
        })) {
          filteredData.push(entry);
        }
      });
      tableData = filteredData;
    }
    this.dataTable.setState({data: tableData.slice(
        pageSize * page,
        pageSize * (page + 1)),});
    this.pagination.setState({
      page: page,
      total: Math.ceil(tableData.length / pageSize),
    });
  }

  showDetails(id, resource, buttons) {
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
      var footerButtons = '';

      buttons.forEach(function(button) {
        footerButtons += '<button type="button" onClick="' +
          button['callback'] + '(\'' + id + '\',\'' + resource +
          '\')" class="btn btn-' + button['style'] + '">' +
          button['name'].capitalize() + '</button>';
      });

      var modalTask = new Modal({
        elementClasses: 'modal-boot',
        header: '<h4 class="modal-title">' +
          resource.substring(0, resource.length - 1).capitalize() +
          ': ' + id + '</h4>',
        body: '<div id="' + resourceId + '"></div>',
        footer: footerButtons,
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

  activateFilter(filterElement, filterCallback) {
    Dom.on(
        document.getElementById(filterElement),
        'input',
        function(event) {
          window[filterCallback](event.target.value)
        });
  }

  setFilter(filter) {
    this.filter = filter;
  }

  static convertToNodes(json) {
    var nodes = new Array();
    var childrenList;
    if (json != null) {
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
    }
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
