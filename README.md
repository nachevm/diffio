| METHOD |          ENDPOINT         |                                                            DESCRIPTION                                                           |
|:------:|:-------------------------:|:--------------------------------------------------------------------------------------------------------------------------------:|
| PUT    | {host}/v1/diff/{id}/left  | Saves the base64 encoded binary data data from the request body on the left side for specified diff id                           |
| PUT    | {host}/v1/diff/{id}/right | Saves the base64 encoded binary data data from the request body on the right side for specified diff id                          |
| GET    | {host}/v1/diff/{id}       | Computes difference of left and right side data of specified diff id and returns outcome with potential offsets and their length |

Potential improvements: Add Swagger

## **Pre-requisites**
1. Java 11+
### **Lombok**
Since Lombok is used, you need to install the appropriate plugin within your IDE (https://projectlombok.org)
### **Code Style**
For those using Intellij IDEA there is already a formatter included in the Project. If it's not enabled by default you can do that in File->Settings->Editor->Code Style-Scheme->Project.
Also make sure that "Enable EditorConfig support" is checked, this can be found in File->Settings->Editor->Code Style

## **Setup**
Run project with: ```gradlew application:bootRun```
