Feature: validating Place API's
@AddPlace
Scenario Outline: Verify if Place is being Succesfully added using AddPlaceAPI

	Given Add Place Payload with "<name>"  "<langauage>"  "<address>" 
	When user calls "AddPlaceAPI" with "Post" http request
	Then the API call Got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify Place_id created maps to "<name>" using "getPlaceAPI"

	
Examples: 
				|name   | langauage |address					 |
				|AAHouse|	English   |world cross center|
	#			|BBhouse|Spanish    |Sea Cross center  |
	
@DeletePlace
Scenario: verify if Delete Place functionality is working
	
		Given DeletePlace payload
		When user calls "deletePlaceAPI" with "Post" http request
		Then the API call Got success with status code 200
		And "status" in response body is "OK"
		