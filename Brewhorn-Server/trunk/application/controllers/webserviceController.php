<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');  

/**
 * 
 * @author Kapil
 */
class webserviceController extends CI_Controller {
	
	public function __construct()
	{
	    parent::__construct();
		$this->load->database();
		$this->load->model('webservicemodel');
	}
	/**
	 *
	 *	Function to register a new user in the application
	 *
	 **/
	function userRegistration(){
		
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<userRegistration>";
		$xml .= "<firstName><![CDATA[Kapil]]></firstName>"; 
		$xml .= "<lastName><![CDATA[Bansal]]></lastName>";
		$xml .= "<username><![CDATA[kapil]]></username>"; 
		$xml .= "<email><![CDATA[aa.bansal@sebiz.net]]></email>"; 
		$xml .= "<password><![CDATA[123456789]]></password>"; 
		$xml .= "<zipcode><![CDATA[140103]]></zipcode>"; 
		$xml .= "<drinkerLevel><![CDATA[My Own Level]]></drinkerLevel>"; 
		$xml .= "</userRegistration>";
		
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("userRegistration"=>"-2"));
		}
		else
		{
			$firstName = trim($loadElement->firstName);					//Load element data into variable
			$lastName = trim($loadElement->lastName);					//Load element data into variable
			$username = trim($loadElement->username);					//Load element data into variable
			$email = trim($loadElement->email);							//Load element data into variable
			$password = trim($loadElement->password);					//Load element data into variable
			$zipcode = trim($loadElement->zipcode);						//Load element data into variable
			$drinkerLevel = trim($loadElement->drinkerLevel);			//Load element data into variable
			
			$response=$this->webservicemodel->userRegistration($firstName,$lastName,$username,$email,$password,$zipcode,$drinkerLevel);//Model Function call 
			
			echo $response;	// return response to mobile.
			die;
		}
	}//ef
	
	
	/** 
	 *
	 *	Function for user Login 
	 *
	 **/
	function userLogin(){
		
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<userLogin>";
		$xml .= "<username><![CDATA[kapilss]]></username>"; 
		$xml .= "<password><![CDATA[123456789]]></password>"; 
		$xml .= "</userLogin>";
		
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("userLogin"=>"-2"));
		}
		else
		{
			$username = trim($loadElement->username);					//Load element data into variable
			$password = trim($loadElement->password);				//Load element data into variable
			
			$response=$this->webservicemodel->userLogin($username,$password);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	
	/**
	 *
	 *	Function to retrieve the user passowrd. In this if user forget his passord then  the same password will send to his email address.
	 *
	 **/
	function forgotPassword(){
		
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<forgotPassword>";
		$xml .= "<username><![CDATA[kapil]]></username>"; 
		$xml .= "</forgotPassword>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("forgotPassword"=>"-2"));
		}
		else
		{
			$username = trim($loadElement->username);					//Load element data into variable
		
			$response=$this->webservicemodel->forgotPassword($username);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	/***
	 **
	 **	Function to save user taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	function userTasteProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<userTasteProfile>";
		$xml .= "<userId><![CDATA[2]]></userId>"; 
		$xml .= "<aroma><![CDATA[1]]></aroma>"; 
		$xml .= "<sweet><![CDATA[1]]></sweet>"; 
		$xml .= "<bitter><![CDATA[1]]></bitter>"; 
		$xml .= "<malt><![CDATA[1]]></malt>"; 
		$xml .= "<yeast><![CDATA[1]]></yeast>"; 
		$xml .= "<mouthFeel><![CDATA[1]]></mouthFeel>"; 
		$xml .= "<sour><![CDATA[1]]></sour>"; 
		$xml .= "<additive><![CDATA[1]]></additive>"; 
		$xml .= "<booziness><![CDATA[1]]></booziness>"; 
		$xml .= "<sour_status><![CDATA[1]]></sour_status>"; 
		$xml .= "<additive_status><![CDATA[1]]></additive_status>"; 
		$xml .= "<booziness_status><![CDATA[1]]></booziness_status>"; 
		$xml .= "<yeast_status><![CDATA[1]]></yeast_status>"; 
		$xml .= "</userTasteProfile>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;

		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("userTasteProfile"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);					//Load element data into variable
			$aroma = trim($loadElement->aroma);					//Load element data into variable
			$sweet = trim($loadElement->sweet);					//Load element data into variable
			$bitter = trim($loadElement->bitter);					//Load element data into variable
			$malt = trim($loadElement->malt);					//Load element data into variable
			$yeast = trim($loadElement->yeast);					//Load element data into variable
			$mouthFeel = trim($loadElement->mouthFeel);					//Load element data into variable
			$sour = trim($loadElement->sour);					//Load element data into variable
			$additive = trim($loadElement->additive);					//Load element data into variable
			$booziness = trim($loadElement->booziness);					//Load element data into variable
			$sour_status = trim($loadElement->sour_status);					//Load element data into variable
			$additive_status = trim($loadElement->additive_status);					//Load element data into variable
			$yeast_status = trim($loadElement->yeast_status);					//Load element data into variable
			$booziness_status = trim($loadElement->booziness_status);					//Load element data into variable
		
			$response=$this->webservicemodel->userTasteProfile($userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$sour_status,$additive_status,$booziness_status,$yeast_status);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	
	/***
	 **
	 **	Function to edit user taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	 
	function editUserTasteProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<editUserTasteProfile>";
		$xml .= "<userId><![CDATA[2]]></userId>"; 
		$xml .= "<aroma><![CDATA[1]]></aroma>"; 
		$xml .= "<sweet><![CDATA[1]]></sweet>"; 
		$xml .= "<bitter><![CDATA[1]]></bitter>"; 
		$xml .= "<malt><![CDATA[1]]></malt>"; 
		$xml .= "<yeast><![CDATA[1]]></yeast>"; 
		$xml .= "<mouthFeel><![CDATA[1]]></mouthFeel>"; 
		$xml .= "<sour><![CDATA[1]]></sour>"; 
		$xml .= "<additive><![CDATA[1]]></additive>"; 
		$xml .= "<booziness><![CDATA[1]]></booziness>"; 
		$xml .= "<sour_status><![CDATA[1]]></sour_status>"; 
		$xml .= "<additive_status><![CDATA[1]]></additive_status>"; 
		$xml .= "<booziness_status><![CDATA[1]]></booziness_status>"; 
		$xml .= "<yeast_status><![CDATA[1]]></yeast_status>"; 
		$xml .= "</editUserTasteProfile>";
		
		
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("editUserTasteProfile"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);					//Load element data into variable
			$aroma = trim($loadElement->aroma);					//Load element data into variable
			$sweet = trim($loadElement->sweet);					//Load element data into variable
			$bitter = trim($loadElement->bitter);					//Load element data into variable
			$malt = trim($loadElement->malt);					//Load element data into variable
			$yeast = trim($loadElement->yeast);					//Load element data into variable
			$mouthFeel = trim($loadElement->mouthFeel);					//Load element data into variable
			$sour = trim($loadElement->sour);					//Load element data into variable
			$additive = trim($loadElement->additive);					//Load element data into variable
			$booziness = trim($loadElement->booziness);					//Load element data into variable
			$sour_status = trim($loadElement->sour_status);					//Load element data into variable
			$additive_status = trim($loadElement->additive_status);					//Load element data into variable
			$booziness_status = trim($loadElement->booziness_status);					//Load element data into variable
			$yeast_status = trim($loadElement->yeast_status);	
			
			$response=$this->webservicemodel->editUserTasteProfile($userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$sour_status,$additive_status,$booziness_status,$yeast_status);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}
	
	/***
	 **
	 **	Function to add user beer
	 ** @params brewery,beerName,beerStyle,abv,ibu,mood,venue,event,hype
	 **
	 ***/
	function addUserBeer(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<addUserBeer>";
		$xml .= "<userId><![CDATA[34]]></userId>"; 
		$xml .= "<brewery><![CDATA[áéúíÁÉÍÓÚüÜüÜñÑ¿¡¼½º]]></brewery>"; 
		$xml .= "<beerName><![CDATA[áéúíÁÉÍÓÚüÜüÜñÑ¿¡¼½º]]></beerName>"; 
		$xml .= "<beerStyle><![CDATA[1]]></beerStyle>"; 
		$xml .= "<abv><![CDATA[1]]></abv>"; 
		$xml .= "<ibu><![CDATA[1]]></ibu>"; 
		$xml .= "<mood><![CDATA[1]]></mood>"; 
		$xml .= "<venue><![CDATA[1]]></venue>"; 
		$xml .= "<event><![CDATA[1]]></event>"; 
		$xml .= "<hype><![CDATA[1]]></hype>"; 
		$xml .= "</addUserBeer>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("addUserBeer"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);					//Load element data into variable
			$brewery = trim($loadElement->brewery);					//Load element data into variable
			$beerName = trim($loadElement->beerName);					//Load element data into variable
			$beerStyle = trim($loadElement->beerStyle);					//Load element data into variable
			$abv = trim($loadElement->abv);					//Load element data into variable
			$ibu = trim($loadElement->ibu);					//Load element data into variable
			$mood = trim($loadElement->mood);					//Load element data into variable
			$venue = trim($loadElement->venue);					//Load element data into variable
			$event = trim($loadElement->event);					//Load element data into variable
			$hype = trim($loadElement->hype);					//Load element data into variable
		
			$response=$this->webservicemodel->addUserBeer($userId,$brewery,$beerName,$beerStyle,$abv,$ibu,$mood,$venue,$event,$hype);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	/***
	 **
	 **	Function to edit user beer
	 ** @params brewery,beerName,beerStyle,abv,ibu,mood,venue,event,hype
	 **
	 ***/
	
	function editUserBeer(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<editUserBeer>";
		$xml .= "<beerId><![CDATA[1]]></beerId>"; 
		$xml .= "<userId><![CDATA[1]]></userId>"; 
		$xml .= "<brewery><![CDATA[55]]></brewery>"; 
		$xml .= "<beerName><![CDATA[1]]></beerName>"; 
		$xml .= "<beerStyle><![CDATA[1]]></beerStyle>"; 
		$xml .= "<abv><![CDATA[1]]></abv>"; 
		$xml .= "<ibu><![CDATA[1]]></ibu>"; 
		$xml .= "<mood><![CDATA[1]]></mood>"; 
		$xml .= "<venue><![CDATA[1]]></venue>"; 
		$xml .= "<event><![CDATA[1]]></event>"; 
		$xml .= "<hype><![CDATA[1]]></hype>"; 
		$xml .= "</editUserBeer>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("editUserBeer"=>"-2"));
		}
		else
		{
			$beerId = trim($loadElement->beerId);					//Load element data into variable
			$userId = trim($loadElement->userId);					//Load element data into variable
			$brewery = trim($loadElement->brewery);					//Load element data into variable
			$beerName = trim($loadElement->beerName);					//Load element data into variable
			$beerStyle = trim($loadElement->beerStyle);					//Load element data into variable
			$abv = trim($loadElement->abv);					//Load element data into variable
			$ibu = trim($loadElement->ibu);					//Load element data into variable
			$mood = trim($loadElement->mood);					//Load element data into variable
			$venue = trim($loadElement->venue);					//Load element data into variable
			$event = trim($loadElement->event);					//Load element data into variable
			$hype = trim($loadElement->hype);					//Load element data into variable
		
			$response=$this->webservicemodel->editUserBeer($beerId,$userId,$brewery,$beerName,$beerStyle,$abv,$ibu,$mood,$venue,$event,$hype);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	
	/***
	 **
	 **	Function to add user beer taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	function addBeerProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<addBeerProfile>";
		$xml .= "<beerId><![CDATA[1]]></beerId>"; 
		$xml .= "<userId><![CDATA[1]]></userId>"; 
		$xml .= "<aroma><![CDATA[1]]></aroma>"; 
		$xml .= "<aroma_cmt><![CDATA[1]]></aroma_cmt>"; 
		$xml .= "<sweet><![CDATA[1]]></sweet>"; 
		$xml .= "<sweet_cmt><![CDATA[1]]></sweet_cmt>"; 
		$xml .= "<bitter><![CDATA[1]]></bitter>"; 
		$xml .= "<bitter_cmt><![CDATA[1]]></bitter_cmt>"; 
		$xml .= "<malt><![CDATA[1]]></malt>"; 
		$xml .= "<malt_cmt><![CDATA[1]]></malt_cmt>"; 
		$xml .= "<yeast><![CDATA[1]]></yeast>"; 
		$xml .= "<yeast_cmt><![CDATA[1]]></yeast_cmt>"; 
		$xml .= "<mouthFeel><![CDATA[1]]></mouthFeel>"; 
		$xml .= "<mouthFeel_cmt><![CDATA[1]]></mouthFeel_cmt>"; 
		$xml .= "<sour><![CDATA[1]]></sour>"; 
		$xml .= "<sour_cmt><![CDATA[1]]></sour_cmt>"; 
		$xml .= "<additive><![CDATA[1]]></additive>"; 
		$xml .= "<additive_cmt><![CDATA[1]]></additive_cmt>"; 
		$xml .= "<booziness><![CDATA[1]]></booziness>"; 
		$xml .= "<booziness_cmt><![CDATA[1]]></booziness_cmt>"; 
		$xml .= "<booziness_cmt1><![CDATA[1]]></booziness_cmt1>"; 
		$xml .= "<mouthFeel_cmt1><![CDATA[1]]></mouthFeel_cmt1>"; 
		$xml .= "</addBeerProfile>";

		global $HTTP_RAW_POST_DATA;

		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement)){
			echo json_encode(array("addBeerProfile"=>"-2"));
		}else{
			$beerId = trim($loadElement->beerId);					//Load element data into variable
			$userId = trim($loadElement->userId);					//Load element data into variable
			$aroma = trim($loadElement->aroma);					//Load element data into variable
			$sweet = trim($loadElement->sweet);					//Load element data into variable
			$bitter = trim($loadElement->bitter);					//Load element data into variable
			$malt = trim($loadElement->malt);					//Load element data into variable
			$yeast = trim($loadElement->yeast);					//Load element data into variable
			$mouthFeel = trim($loadElement->mouthFeel);					//Load element data into variable
			$sour = trim($loadElement->sour);					//Load element data into variable
			$additive = trim($loadElement->additive);					//Load element data into variable
			$booziness = trim($loadElement->booziness);					//Load element data into variable
			
			$aroma_cmt = trim($loadElement->aroma_cmt);					//Load element data into variable
			$sweet_cmt = trim($loadElement->sweet_cmt);					//Load element data into variable
			$bitter_cmt = trim($loadElement->bitter_cmt);					//Load element data into variable
			$malt_cmt = trim($loadElement->malt_cmt);					//Load element data into variable
			$yeast_cmt = trim($loadElement->yeast_cmt);					//Load element data into variable
			$mouthFeel_cmt = trim($loadElement->mouthFeel_cmt);					//Load element data into variable
			$sour_cmt = trim($loadElement->sour_cmt);					//Load element data into variable
			$additive_cmt = trim($loadElement->additive_cmt);					//Load element data into variable
			$booziness_cmt = trim($loadElement->booziness_cmt);					//Load element data into variable
			$booziness_cmt1 = trim($loadElement->booziness_cmt1);					//Load element data into variable
			$mouthFeel_cmt1 = trim($loadElement->mouthFeel_cmt1);					//Load element data into variable
		
			$response=$this->webservicemodel->addBeerProfile($beerId,$userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$aroma_cmt,$sweet_cmt,$bitter_cmt,$malt_cmt,$yeast_cmt,$mouthFeel_cmt,$sour_cmt,$additive_cmt,$booziness_cmt,$booziness_cmt1,$mouthFeel_cmt1);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	
	/***
	 **
	 **	Function to edit user beer taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	function editBeerProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<editBeerProfile>";
		$xml .= "<beerId><![CDATA[1]]></beerId>"; 
		$xml .= "<userId><![CDATA[1]]></userId>"; 
		$xml .= "<aroma><![CDATA[1]]></aroma>"; 
		$xml .= "<aroma_cmt><![CDATA[1]]></aroma_cmt>"; 
		$xml .= "<sweet><![CDATA[1]]></sweet>"; 
		$xml .= "<sweet_cmt><![CDATA[1]]></sweet_cmt>"; 
		$xml .= "<bitter><![CDATA[1]]></bitter>"; 
		$xml .= "<bitter_cmt><![CDATA[1]]></bitter_cmt>"; 
		$xml .= "<malt><![CDATA[1]]></malt>"; 
		$xml .= "<malt_cmt><![CDATA[1]]></malt_cmt>"; 
		$xml .= "<yeast><![CDATA[1]]></yeast>"; 
		$xml .= "<yeast_cmt><![CDATA[1]]></yeast_cmt>"; 
		$xml .= "<mouthFeel><![CDATA[1]]></mouthFeel>"; 
		$xml .= "<mouthFeel_cmt><![CDATA[1]]></mouthFeel_cmt>"; 
		$xml .= "<sour><![CDATA[1]]></sour>"; 
		$xml .= "<sour_cmt><![CDATA[1]]></sour_cmt>"; 
		$xml .= "<additive><![CDATA[1]]></additive>"; 
		$xml .= "<additive_cmt><![CDATA[1]]></additive_cmt>"; 
		$xml .= "<booziness><![CDATA[1]]></booziness>"; 
		$xml .= "<booziness_cmt><![CDATA[1]]></booziness_cmt>"; 
		$xml .= "<booziness_cmt1><![CDATA[1]]></booziness_cmt1>"; 
		$xml .= "<mouthFeel_cmt1><![CDATA[1]]></mouthFeel_cmt1>"; 
		$xml .= "</editBeerProfile>";
		
		global $HTTP_RAW_POST_DATA;

		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement)){
			echo json_encode(array("editBeerProfile"=>"-2"));
		}else{

			$beerId = trim($loadElement->beerId);					//Load element data into variable
			$userId = trim($loadElement->userId);					//Load element data into variable
			$aroma = trim($loadElement->aroma);						//Load element data into variable
			$sweet = trim($loadElement->sweet);						//Load element data into variable
			$bitter = trim($loadElement->bitter);					//Load element data into variable
			$malt = trim($loadElement->malt);						//Load element data into variable
			$yeast = trim($loadElement->yeast);						//Load element data into variable
			$mouthFeel = trim($loadElement->mouthFeel);				//Load element data into variable
			$sour = trim($loadElement->sour);						//Load element data into variable
			$additive = trim($loadElement->additive);				//Load element data into variable
			$booziness = trim($loadElement->booziness);				//Load element data into variable
			
			$aroma_cmt = trim($loadElement->aroma_cmt);				//Load element data into variable
			$sweet_cmt = trim($loadElement->sweet_cmt);				//Load element data into variable
			$bitter_cmt = trim($loadElement->bitter_cmt);			//Load element data into variable
			$malt_cmt = trim($loadElement->malt_cmt);				//Load element data into variable
			$yeast_cmt = trim($loadElement->yeast_cmt);				//Load element data into variable
			$mouthFeel_cmt = trim($loadElement->mouthFeel_cmt);		//Load element data into variable
			$sour_cmt = trim($loadElement->sour_cmt);				//Load element data into variable
			$additive_cmt = trim($loadElement->additive_cmt);		//Load element data into variable
			$booziness_cmt = trim($loadElement->booziness_cmt);		//Load element data into variable
			$booziness_cmt1 = trim($loadElement->booziness_cmt1);					//Load element data into variable
			$mouthFeel_cmt1 = trim($loadElement->mouthFeel_cmt1);					//Load element data into variable
		
			$response=$this->webservicemodel->editBeerProfile($beerId,$userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$aroma_cmt,$sweet_cmt,$bitter_cmt,$malt_cmt,$yeast_cmt,$mouthFeel_cmt,$sour_cmt,$additive_cmt,$booziness_cmt,$booziness_cmt1,$mouthFeel_cmt1);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	/***
	 **
	 **	Function to get Beer Profile correspond to beer Id
	 **
	 ***/
	 
	Function getBeerProfile($beerId=NULL){
		$beerId = $_GET['beerId'];
		$response=$this->webservicemodel->getBeerProfile($beerId);//Model Function call 
		echo $response;								// return response to mobile.
		die;
	}//ef
	
	
	/***
	 **
	 **	Function to search beer
	 **
	 ***/
	Function searchBeer(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<searchBeer>";
		$xml .= "<userId><![CDATA[2]]></userId>"; 
		$xml .= "<beerName><![CDATA[Mad River]]></beerName>"; 
		$xml .= "</searchBeer>";
		
		global $HTTP_RAW_POST_DATA;
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement)){
			echo json_encode(array("searchBeer"=>"-2"));
		}else{
			$userId = trim($loadElement->userId);					//Load element data into variable
			$beerName = trim($loadElement->beerName);					//Load element data into variable
			$response=$this->webservicemodel->searchBeer($userId,$beerName);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}
	
	/***
	 **
	 ** function to get user taste profile 
	 **
	 ***/
	Function getUserTasteProfile($userId=NULL){
		$userId = $_GET['userId'];
		$response=$this->webservicemodel->getUserTasteProfile($userId);//Model Function call 
		echo $response;								// return response to mobile.
		die;
	}//ef
	
	/***
	 **
	 ** function to get user Profile
	 **
	 ***/
	
	Function userProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<userProfile>";
		$xml .= "<userId><![CDATA[1]]></userId>"; 
		$xml .= "<type><![CDATA[2]]></type>"; 
		$xml .= "</userProfile>";

		global $HTTP_RAW_POST_DATA;
		
		$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("userProfile"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);									//Load element data into variable
			$type = trim($loadElement->type);					//Load element data into variable
			$response=$this->webservicemodel->userProfile($userId,$type);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}
	
	/***
	 **
	 ** function to edit user Profile
	 **
	 ***/
	
	Function editUserProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<editUserProfile>";
		$xml .= "<userId><![CDATA[1]]></userId>"; 
		$xml .= "<firstName><![CDATA[Qwerty]]></firstName>"; 
		$xml .= "<lastName><![CDATA[Qwerty]]></lastName>"; 
		$xml .= "<zipcode><![CDATA[555556]]></zipcode>"; 
		$xml .= "<drinkerLevel><![CDATA[Thinking about craft]]></drinkerLevel>"; 
		$xml .= "<email><![CDATA[cgg@g.com]]></email>"; 
		$xml .= "<username><![CDATA[qwerty]]></username>"; 
		$xml .= "</editUserProfile>";

		global $HTTP_RAW_POST_DATA;

		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("editUserProfile"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);									//Load element data into variable
			$firstName = trim($loadElement->firstName);					//Load element data into variable
			$lastName = trim($loadElement->lastName);					//Load element data into variable
			$drinkerLevel = trim($loadElement->drinkerLevel);					//Load element data into variable
			$email = trim($loadElement->email);					//Load element data into variable
			$username = trim($loadElement->username);					//Load element data into variable
			$zipcode = trim($loadElement->zipcode);					//Load element data into variable
			
			$response=$this->webservicemodel->editUserProfile($userId,$firstName,$lastName,$zipcode,$drinkerLevel,$email,$username);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	/**
	 *	Function to change User Profile Password.
	 **/
	 
	Function changePassword(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<changePassword>";
		$xml .= "<userId><![CDATA[8]]></userId>"; 
		$xml .= "<password><![CDATA[12345678]]></password>"; 
		$xml .= "<newPassword><![CDATA[123456789]]></newPassword>"; 
		$xml .= "</changePassword>";

		global $HTTP_RAW_POST_DATA;

		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("changePassword"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);									//Load element data into variable
			$password = trim($loadElement->password);					//Load element data into variable
			$newPassword = trim($loadElement->newPassword);					//Load element data into variable
			$response=$this->webservicemodel->changePassword($userId,$password,$newPassword);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}//ef
	
	/**
	 *	Function to get User Beer History.
	 **/
	 
	Function beerHistory(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<beerHistory>";
		$xml .= "<userId><![CDATA[9]]></userId>"; 
		$xml .= "</beerHistory>";

		global $HTTP_RAW_POST_DATA;

		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("beerHistory"=>"-2"));
		}
		else
		{
			$userId = trim($loadElement->userId);									//Load element data into variable
			
			$response=$this->webservicemodel->beerHistory($userId);//Model Function call 
			echo $response;								// return response to mobile.
			die;
		}
	}
	
	
}