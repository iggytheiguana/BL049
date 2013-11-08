
#ifndef CraftsBeerApp_h
#define CraftsBeerApp_h

///Internet & Server Error
#ifdef LOCAL
#define kLoginServerUrl @"http://localhost/index.php/webserviceController/userLogin"
// home screen
#define kBrreUrl @"http://localhost/index.php/webserviceController/searchBeer"
#define kUserProfile @"http://localhost/index.php/webserviceController/userProfile"
#define kEditUserProfile @"http://localhost/index.php/webserviceController/editUserProfile"
#define kUserTaste @"http://localhost/index.php/webserviceController/userTasteProfile"
#define kNewBeerTaste @"http://localhost/index.php/webserviceController/addBeerProfile"
#define kEditUserBeerTaste @"http://localhost/index.php/webserviceController/editUserTasteProfile"
#define kEditBeer @"http://localhost/index.php/webserviceController/editUserBeer"
#define kGetBeerDetails @"http://localhost/index.php/webserviceController/getBeerProfile?beerId=%@"
#define kAddBeer @"http://localhost/index.php/webserviceController/addUserBeer"
#define kChangePassword @"http://localhost/index.php/webserviceController/changePassword"
#define kHistory @"http://localhost/index.php/webserviceController/beerHistory"
#define  kRegServerUrl @"http://localhost/index.php/webserviceController/userRegistration"
#define kFrgtPswdServerUrl @"http://localhost/index.php/webserviceController/forgotPassword"

#else
#define kLoginServerUrl @"http://brewhorn.com/app_data/webserviceController/userLogin"
// home screen
#define kBrreUrl @"http://brewhorn.com/app_data/webserviceController/searchBeer"
#define kUserProfile @"http://brewhorn.com/app_data/webserviceController/userProfile"
#define kEditUserProfile @"http://brewhorn.com/app_data/webserviceController/editUserProfile"
#define kUserTaste @"http://brewhorn.com/app_data/webserviceController/userTasteProfile"
#define kNewBeerTaste @"http://brewhorn.com/app_data/webserviceController/addBeerProfile"
#define kEditUserBeerTaste @"http://brewhorn.com/app_data/webserviceController/editUserTasteProfile"
#define kEditBeer @"http://brewhorn.com/app_data/webserviceController/editUserBeer"
#define kGetBeerDetails @"http://brewhorn.com/app_data/webserviceController/getBeerProfile?beerId=%@"
#define kAddBeer @"http://brewhorn.com/app_data/webserviceController/addUserBeer"
#define kChangePassword @"http://brewhorn.com/app_data/webserviceController/changePassword"
#define kHistory @"http://brewhorn.com/app_data/webserviceController/beerHistory"
#define  kRegServerUrl @"http://brewhorn.com/app_data/webserviceController/userRegistration"
#define kFrgtPswdServerUrl @"http://brewhorn.com/app_data/webserviceController/forgotPassword"
#endif


#define kAlertInternet @"Please connect to internet."
#define kAlertSeverErr @"Server not responding."

//  Login
#define LOGIN_XML @"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userLogin><username><![CDATA[%@]]></username><password><![CDATA[%@]]></password></userLogin>"


// Register

#define  SIGNUP_XML  @"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userRegistration><firstName><![CDATA[%@]]></firstName><lastName><![CDATA[%@]]></lastName><username><![CDATA[%@]]></username><email><![CDATA[%@]]></email><password><![CDATA[%@]]></password><zipcode><![CDATA[%@]]></zipcode><drinkerLevel><![CDATA[%@]]></drinkerLevel></userRegistration>"



/// user profile



#define  UserProfile_XML  @"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userProfile><userId><![CDATA[%@]]></userId><type><![CDATA[2]]></type></userProfile>"

// edit user profile

#define  EditUserProfile_XML  @"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editUserProfile><userId><![CDATA[%@]]></userId><firstName><![CDATA[%@]]></firstName><lastName><![CDATA[%@]]></lastName><zipcode><![CDATA[%@]]></zipcode><drinkerLevel><![CDATA[%@]]></drinkerLevel><email><![CDATA[%@]]></email><username><![CDATA[%@]]></username></editUserProfile>"




//  add beer


//# define kAddBeer @"https://api.brewerydb.com/v2/beers?key=75ca1c53de40741205e76c8c3a7464c7&ids=BWQehj/beers"


// change password

#define ChangePassword_XML  @"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><changePassword><userId><![CDATA[%@]]></userId><password><![CDATA[%@]]></password><newPassword><![CDATA[%@]]></newPassword></changePassword>"



///  History

#define History_XML  @"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><beerHistory><userId><![CDATA[%@]]></userId></beerHistory>"



#endif
