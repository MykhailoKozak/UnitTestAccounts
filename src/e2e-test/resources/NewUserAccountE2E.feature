Feature: User to create account

  Scenario: Ability for user to register, to create and update accoutn
    Given browser opened at http://localhost:8080/register url
    When user submit registration form with attributes:
      | firstName | Mykhailo                   |
      | lastName  | Kozak                      |
      | email     | mykhailokozaknew@gmail.com |
      | password  | 2222                       |
    Then user is logged in and located on home page
    And user model exists in database with attributes:
      | firstName | Mykhailo                   |
      | lastName  | Kozak                      |
      | email     | mykhailokozaknew@gmail.com |
      | password  | 2222                       |
    When browser opened at http://localhost:8080/accounts/edit/new url
    And user submit create-update account form with attributes:
      | accountCode    | adidas-ftw-2                                      |
      | accountName    | Adidas Footwear department                        |
      | accountImage   | http://pngimg.com/uploads/adidas/adidas_PNG18.png |
      | accountBalance | 0.0                                               |
    Then account details displayed on accounts grid page
      | accountCode    | adidas-ftw-2               |
      | accountName    | Adidas Footwear department |
      | accountBalance | 0.0                        |
    And account model exists in database with attributes:
      | code       | adidas-ftw-2                                      |
      | name       | Adidas Footwear department                        |
      | urlToImage | http://pngimg.com/uploads/adidas/adidas_PNG18.png |
      | balance    | 0.0                                               |
    When browser opened at http://localhost:8080/accounts/edit/adidas-ftw-2 url
    And user submit create-update account form with attributes:
      | accountName    | Adidas Footwear department 1                      |
      | accountImage   | http://pngimg.com/uploads/adidas/adidas_PNG18.png |
      | accountBalance | 250000.0                                          |
    Then account details displayed on accounts grid page
      | accountCode    | adidas-ftw-2                 |
      | accountName    | Adidas Footwear department 1 |
      | accountBalance | 250000.0                     |
    Then account model exists in database with attributes:
      | code       | adidas-ftw-2                                      |
      | name       | Adidas Footwear department 1                      |
      | urlToImage | http://pngimg.com/uploads/adidas/adidas_PNG18.png |
      | balance    | 250000.0                                          |


  Scenario: Ability for user to login and view accounts
    Given browser opened at http://localhost:8080 url
    And user submit login form with attributes:
      | email    | mykhailokozaknew@gmail.com |
      | password | 2222                       |
    Then account details displayed on accounts grid page
      | accountCode | adidas-ftw-2                 |
      | accountName | Adidas Footwear department 1 |

  Scenario: Ability for failed user to login and view accounts
    Given browser opened at http://localhost:8080 url
    And user submit login form with attributes:
      | email    | mykhailokoz@gmail.com |
      | password | 222                   |
    Then account details displayed on accounts grid page
      | accountCode | adidas-ftw-2                 |
      | accountName | Adidas Footwear department 1 |
