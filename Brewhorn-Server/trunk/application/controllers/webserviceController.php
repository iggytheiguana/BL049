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
		$this->load->helper('string_helper');	
	}
	
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
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
		
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement))
		{
			echo json_encode(array("userRegistration"=>"-2"));
		}
		else
		{
			$firstName = trim($loadElement->firstName);					//Load element data into variable
			$lastName = trim($loadElement->lastName);						//Load element data into variable
			$username = trim($loadElement->username);					//Load element data into variable
			$email = trim($loadElement->email);					//Load element data into variable
			$password = trim($loadElement->password);				//Load element data into variable
			$zipcode = trim($loadElement->zipcode);				//Load element data into variable
			$drinkerLevel = trim($loadElement->drinkerLevel);				//Load element data into variable
			
			$response=$this->webservicemodel->userRegistration($firstName,$lastName,$username,$email,$password,$zipcode,$drinkerLevel);//Model Function call 
			
			echo $response;								// return response to mobile.
			die;
		}
	}
	
	function userLogin(){
		
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<userLogin>";
		$xml .= "<username><![CDATA[kapilss]]></username>"; 
		$xml .= "<password><![CDATA[123456789]]></password>"; 
		$xml .= "</userLogin>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
	function forgotPassword(){
		
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<forgotPassword>";
		$xml .= "<username><![CDATA[kapil]]></username>"; 
		$xml .= "</forgotPassword>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
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
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
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
		
		/* $rahi = '<?xml version="1.0" encoding="UTF-8" ?><editUserTasteProfile><beerId><![CDATA[8]]></beerId><userId><![CDATA[8]]></userId><aroma><![CDATA[3]]></aroma><sweet><![CDATA[5]]></sweet><bitter><![CDATA[7]]></bitter><malt><![CDATA[4]]></malt><yeast><![CDATA[3]]></yeast><mouthFeel><![CDATA[3]]></mouthFeel><sour><![CDATA[5]]></sour><additive><![CDATA[3]]></additive><booziness><![CDATA[4]]></booziness><sour_status><![CDATA[1]]></sour_status><additive_status><![CDATA[1]]></additive_status><booziness_status><![CDATA[1]]></booziness_status></editUserTasteProfile>'; */
		
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
		/* $xml = utf8_encode($xml); */
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
	}
	
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
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
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
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
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
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		
		if(!is_object($loadElement)){
			echo json_encode(array("editBeerProfile"=>"-2"));
		}else{
		//echo "<pre>";print_R($loadElement);
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
	}
	
	Function getBeerProfile($beerId=NULL){
		$beerId = $_GET['beerId'];
		$response=$this->webservicemodel->getBeerProfile($beerId);//Model Function call 
		echo $response;								// return response to mobile.
		die;
	}
	
	Function searchBeer(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<searchBeer>";
		$xml .= "<userId><![CDATA[2]]></userId>"; 
		$xml .= "<beerName><![CDATA[Mad River]]></beerName>"; 
		$xml .= "</searchBeer>";
		//echo $xml;exit;
		
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
	
	Function getUserTasteProfile($userId=NULL){
		$userId = $_GET['userId'];
		$response=$this->webservicemodel->getUserTasteProfile($userId);//Model Function call 
		echo $response;								// return response to mobile.
		die;
	}
	
	Function recommentBeer($userId=NULL){
		$userId = $_GET['userId'];
		$response=$this->webservicemodel->recommentBeer($userId);//Model Function call 
		echo $response;								// return response to mobile.
		die;
	}
	
	Function userProfile(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<userProfile>";
		$xml .= "<userId><![CDATA[1]]></userId>"; 
		$xml .= "<type><![CDATA[2]]></type>"; 
		$xml .= "</userProfile>";
		//echo $xml;exit;
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
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
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
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	}
	
	/**
	 *	Function to get User Beer History.
	 **/
	 
	Function beerHistory(){
		$xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"; 
		$xml .= "<beerHistory>";
		$xml .= "<userId><![CDATA[9]]></userId>"; 
		$xml .= "</beerHistory>";
		//echo $xml;exit;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
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
	
	Function testXML(){
		$xml = '<?xml version="1.0" encoding="UTF-8" ?><addUserBeer><userId><![CDATA[16]]></userId><brewery><![CDATA[ëk]]></brewery><beerName><![CDATA[ëk]]></beerName><beerStyle><![CDATA[]]></beerStyle><abv><![CDATA[]]></abv><ibu><![CDATA[]]></ibu><mood><![CDATA[]]></mood><venue><![CDATA[]]></venue><event><![CDATA[]]></event><hype><![CDATA[]]></hype></addUserBeer>';
		//echo $xml;exit;
		//$xmlq = utf8_encode($xml);
		//echo $xmlq;die;
		
		$stories = '<?xml version="1.0" encoding="ISO-8859-1" ?><addUserBeer><userId><![CDATA[2]]></userId><brewery><![CDATA[]]></brewery><beerName><![CDATA[áéúíÁÉÍÓÚüÜüÜñÑ¿¡¼½º]]></beerName><beerStyle><![CDATA[]]></beerStyle><abv><![CDATA[]]></abv><ibu><![CDATA[]]></ibu><mood><![CDATA[]]></mood><venue><![CDATA[]]></venue><event><![CDATA[]]></event><hype><![CDATA[]]></hype></addUserBeer>';
		$loadElement = simplexml_load_string($stories, 'SimpleXMLElement', LIBXML_NOCDATA); 
		
		print_R($loadElement);die;
		global $HTTP_RAW_POST_DATA;
		//$HTTP_RAW_POST_DATA = utf8_encode($HTTP_RAW_POST_DATA);
		$loadElement = simplexml_load_string($HTTP_RAW_POST_DATA, 'SimpleXMLElement', LIBXML_NOCDATA); 	//Interprets a string of XML into an object
		echo "<pre>";print_R($loadElement);die;
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
	}

    Function BreweryDBWebHooks(){
    
    	$activityName = 'BreweryDBWebHooks:';
		log_message('debug', "{$activityName} beginning with POST data: " . implode_with_key($_POST,'>',','));

        if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        	log_message('error', 'This must be accessed via POST');
            throw new Exception('This must be accessed via POST');
        }

        $apiKey = '75ca1c53de40741205e76c8c3a7464c7'; // your BreweryDB API key

        // checks to make sure that the request was actually sent from the BreweryDB API
        $isValidRequest = sha1($apiKey . $_GET['nonce']) == $_GET['key'];
//        $isValidRequest = true;
        if ($isValidRequest) {

            // this will be one of beer, brewery, location, guild, or event
            $attribute = $_POST['attribute'];
            if($attribute == 'beer'){           //go inside only attribute is beer otherwise ignored


                $attributeId = $_POST['attributeId'];

                // Either insert, delete, or edit
                $action = $_POST['action'];

                // The subaction that was taken on the attribute. Either 'none' or various other options like 'socialaccount-update' or 'beer-add'
                $subAction = isset($_POST['subAction'])?$_POST['subAction']:'';

                // The ID of the sub-attribute that was changed.
                //This will only be present if the subAction field is also set.
                //So if a social account was added to a brewery, the subAttributeId will be the ID of the new social account.
                $subAttributeId = isset($_POST['subAttributeId'])?$_POST['subAttributeId']:'';

                // Timestamp that the change was integrated
                $timestamp = $_POST['timestamp'];
                $post_data = serialize($_POST);

                log_message('debug', "{$activityName} attribute={$attribute} action={$action}, attributeId={$attributeId}, subAction={$subAction}, subAttributeId={$subAttributeId}, timestamp={$timestamp}");

                $brewery_data = $this->webservicemodel->BreweryDBWebHooksInsert($attribute,$attributeId,$action,$subAction,$subAttributeId,$timestamp,$post_data);
                //echo 'Inserted record id:'.$brewery_data;

                log_message('debug', "Logged Web Hook Message into brewerydata table with id={$brewery_data}");

                //$action equal to insert or edit
                if(($action == 'insert') || ($action == 'edit')){

                      //insert beer into userbeer and beer table
                      if($action == 'insert'){
                      //get beer data from brewerydb
                        $url_beer = 'http://api.brewerydb.com/v2/beer/'.$attributeId.'?key='.$apiKey.'&format=php&withBreweries=Y';
                        $result_allbeer = $this->get_web_page($url_beer);
                        if($result_allbeer['message'] == 'Request Successful'){
                            if($result_allbeer['data']){
                                $beerdata = $result_allbeer['data'];
                                log_message('debug', "Successfully retrieved from url:{$url_beer} this data: ". implode_with_key($beerdata,'>',','));
                                $this->webservicemodel->InsertUpdateBeer($beerdata,$timestamp,$action);
                            }
                        }
                      }



                      //Edit case for beer attribute only
                      if(($action == 'edit') && ($subAction == 'none')){
                        //get beer data from brewerydb
                        $url_beer = 'http://api.brewerydb.com/v2/beer/'.$attributeId.'?key='.$apiKey.'&format=php&withBreweries=Y';
                        $result_allbeer = $this->get_web_page($url_beer);
                        if($result_allbeer['message'] == 'Request Successful'){
                            if($result_allbeer['data']){
                            //update beer and userbeertable
                            $beerdata = $result_allbeer['data'];
                            log_message('debug', "Successfully retrieved from url:{$url_beer} data: ". implode_with_key($beerdata,'>',','));
                                $this->webservicemodel->InsertUpdateBeer($beerdata,$timestamp,$action);
                            }
                        }
                      }
                      //edit case for related attribute
                      if(($action == 'edit') && ($subAction != 'none')){

                          //devide subaction into two varialbe
                          //1)subaction type is brewery,ingredient,socialaccount and 2)related action for subaction is insert,edit,delete
                          $subAction_array = explode('-',$subAction);
                          $subAction_type = $subAction_array[0];
                          $subAction_typeAction = $subAction_array[1];
                          if($subAction_type == 'brewery'){
                              //get beer data from brewerydb
                              $url_beer = 'http://api.brewerydb.com/v2/beer/'.$attributeId.'?key='.$apiKey.'&format=php&withBreweries=Y';
                              $result_allbeer = $this->get_web_page($url_beer);
                              if($result_allbeer['message'] == 'Request Successful'){
                                  if($result_allbeer['data']){

                                    if(($subAction_typeAction == 'insert') || ($subAction_typeAction == 'edit')){
                                        //insert update case for beerbrewery table
                                        $beerdata = $result_allbeer['data'];
                                        log_message('debug', "Successfully retrieved from url:{$url_beer} data:". implode_with_key($beerdata,'>',','));
                                        $this->webservicemodel->InsertUpdateBeerBrewery($beerdata,$subAttributeId,$timestamp,$subAction_typeAction);
                                    }
                                 }
                               }

                              if($subAction_typeAction == 'delete'){
                              //nothing
                              }
                          }

                          if($subAction_type == 'socialaccount'){
                              //get data from brewery db for socialaccount
                              $url_socialaccount =  'http://api.brewerydb.com/v2/beer/'.$attributeId.'?key='.$apiKey.'&withBreweries=Y&withSocialAccounts=Y&format=php';
                              $result_socialaccount = $this->get_web_page($url_socialaccount);
                              if(($subAction_typeAction == 'insert') || ($subAction_typeAction == 'edit')){
                                if($result_socialaccount['message'] == 'Request Successful'){

                                  //Insert update case for socialmediaacct table
                                  $beerdata = $result_socialaccount['data'];
                                  log_message('debug', "Successfully retrieved from url:{$url_socialaccount} data:". implode_with_key($beerdata,'>',','));
                                  $this->webservicemodel->InsertUpdateSocialMediaAcct($beerdata,$subAttributeId,$subAction_typeAction,$timestamp);
                                }
                              }
                              if($subAction_typeAction == 'delete'){
                                  //Delete case for socialmediaacct table
                                  if($result_socialaccount['message'] == 'Request Successful'){

                                    //Insert update case for socialmediaacct table
                                    $beerdata = $result_socialaccount['data'];
                                    log_message('debug', "Successfully retrieved from url:{$url_socialaccount} data: ". implode_with_key($beerdata,'>',','));
                                    $this->webservicemodel->DeleteSocialMediaAcct($beerdata,$subAttributeId);
                                  }
                              }
                          }

                          if($subAction_type == 'ingredient'){
                              if($subAction_typeAction == 'insert'){
                                  //get data for ingredient from brewery db database
                                  $url_ingredient =  'http://api.brewerydb.com/v2/beer/'.$attributeId.'/ingredients/?key='.$apiKey.'&format=php';
                                  $result_ingredient = $this->get_web_page($url_ingredient);
                                  if($result_ingredient['message'] == 'Request Successful'){
                                      $ingredientdata = $result_ingredient['data'];
                                      //insert into beeringredient table
                                      log_message('debug', "Successfully retrieved from url:{$url_ingredient} data: ". implode_with_key($url_ingredient,'>',','));
                                      $this->webservicemodel->InsertIngredient($ingredientdata,$attributeId,$subAttributeId,$timestamp);
                                  }

                              }
                              if($subAction_typeAction == 'delete'){
                                  //delete case for beeringredient table
                                  $this->webservicemodel->DeleteIngredient($attributeId,$subAttributeId);
                              }
                          }

                    }




                }
                //$action equal to delete
                if($action == 'delete'){

                }
                 // echo 'Script run successfully';


                // Now you can take whatever action you need based
                // on the information you received

                // The best practice is to put this data in a queue
                // somewhere locally for you to process it in an
                // asynchronous manner so you don't get your webhoook
                // disabled because it takes too long to respond.
            }

        } else {
            throw new Exception('Key was not valid');
        }
    }




    function get_web_page($url) {
        $options = array(CURLOPT_RETURNTRANSFER => true, // return web page
        CURLOPT_HEADER => false, // don't return headers
        CURLOPT_FOLLOWLOCATION => true, // follow redirects
        CURLOPT_ENCODING => "", // handle all encodings
        CURLOPT_USERAGENT => "", // who am i
        CURLOPT_AUTOREFERER => true, // set referer on redirect
        CURLOPT_CONNECTTIMEOUT => 120, // timeout on connect
        CURLOPT_TIMEOUT => 120, // timeout on response
        CURLOPT_MAXREDIRS => 10, // stop after 10 redirects
        );
        $ch = curl_init($url);
        curl_setopt_array($ch, $options);
        $content = curl_exec($ch);
        $err = curl_errno($ch);
        $errmsg = curl_error($ch);
        $header = curl_getinfo($ch);
		curl_close($ch);

        $array = unserialize($content);
		//echo "<pre>";print_R($array);die;
        return $array;
    }

}