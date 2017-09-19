# Customisable application portlet

## Syntax of json of parameters configuration
Simplest example of json file:
```
{
    (required) "version_of_portlet_description": 0.2,
    (optional) "tabs": [],
    (required) "parameters": []
}
```
### Syntax of tabs object
* **tabs** object should contains JSON array of strings
* example:
```
"tabs" = ["Tools", "Configuration"] 
```
### Syntax of parameters object
* **parameters** objects should contains JSON array of JSON objects:
```
{
    (optional) "tab": NUMBER_OF_TAB_ITEM, 
    (required) "type": TYPE_NAME,
    (required) "value": VALUE,
    (required) "name": NAME_OF_PARAMETER,
    (required) "display": DISPLAY,
    (optional) "choosen": CHOOSEN_VALUE,
    (optional) "maxlength": LENGTH,
    (optional) "component_map": MAP
}
```
* NUMBER_OF_TAB_ITEM: JSON number - position in the **"tabs"** table
* TYPE_NAME: available parameter types: "password", "text", "list", "radio" and "onedata"
* VALUE: 
  * for "password" type: JSON string value,
  * for "text" type: JSON string value or JSON number value
  * for "list" type: JSON array of strings or numbers
  * for "radio" type: JSON array of strings or numbers
  * for "onedata" type: JSON array of strings with url of OneZones
* NAME_OF_PARAMETER: unique name of parameter (name must be single word e.g. use _ as separator between words)
* DISPLAY: parameter named shown for user in request pop-up 
* CHOOSEN_VALUE: (works only for list and radio types) value selected in the list or radio
* LENGTH: (works only for text and password types) max length of input
* MAP: (works only with onedata type) set of onedata elements and the associated parameter


* example with list type:
```
{
    "tab": 0,
    "value": [
        1,
        2,
        4,
        8
    ],
    "type": "list",
    "name": "number_cpus",
    "display": "Virtual CPUs Number",
    "choosen": 8
}
```
* example with text type:
```
{
    "tab": 1,
    "value": "Paste here your public key",
    "type": "text",
    "name": "instance_key_pub",
    "display": "SSH public key",
    "maxlength": 1000 
}
```
* example with onedata type:
```
{
    "display": "Configuration file",
    "name": "input_config_file",
    "type": "onedata",
    "value": ["https://onezone.cloud.cnaf.infn.it", "https://ozone01.ncg.ingrid.pt"],
    "component_map": {
        "token": "input_onedata_token",
        "space": "input_onedata_space",
        "provider": "input_onedata_providers",
        "path": "input_path",
        "file": "input_config_file"
    },
    "tab": 0
}
```
