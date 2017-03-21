# Customisable application portlet

## Syntax of template-parameters.json
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
    (required) "display": DISPLAY
}
```
* NUMBER_OF_TAB_ITEM: JSON number - position in the **"tabs"** table
* TYPE_NAME: available parameter types: "password", "text", "list" and "radio"
* VALUE: 
  * for "password" type: JSON string value,
  * for "text" type: JSON string value or JSON number value
  * for "list" type: JSON array of strings or numbers
  * for "radio" type: JSON array of strings or numbers
* NAME_OF_PARAMETER: unique name of parameter (name must be single word e.g. use _ as separator between words)
* DISPLAY: parameter named shown for user in request pop-up 



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
    "display": "Virtual CPUs Number"
}
```
* example with text type:
```
{
    "tab": 1,
        "value": "Paste here your public key",
        "type": "text",
        "name": "instance_key_pub",
        "display": "SSH public key"
}
```
