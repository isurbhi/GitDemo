Feature: validating Place API's

@AddPlace
Scenario Outline: Verify if place is being Successfully added using addPlace API
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When User calls "AddPlaceAPI" with "POST" http Request
		Then The API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify place_Id created maps to "<name>" using "getPlaceAPI"

Examples:
	|	name		|	language	|	address		|
	|	User01	|	English		|	address1	|
#	|	User02	|	Hindi			|	address2	|
	
@DeletePlace	
Scenario: Verify if delete place functionality is working
    Given DeletePlace Payload
    When User calls "deletePlaceAPI" with "POST" http Request
    Then The API call got success with status code 200
    And "status" in response body is "OK" 
		