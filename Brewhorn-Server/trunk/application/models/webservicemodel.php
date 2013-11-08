<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

define('timeStamp',strtotime(date("Y-m-d H:i:s")));
/**
 * 
 * @author Kapil
 */

/**
 * Webservice model
 **/

class webservicemodel extends CI_Model {


    public function __construct() {
        parent::__construct();
        $this -> load -> database();
    }
	
	/**************************************************************************************
	*								User Registration									  *
	**************************************************************************************/
	
	Function userRegistration($firstName,$lastName,$username,$email,$password,$zipcode,$drinkerLevel){
		$getEmailCount = $this->db->select()->where('email',$email)->from('userRegistration')->get()->num_rows();
		if($getEmailCount > 0):
			// Email Already Exists.
			return json_encode(array("userRegistration"=>"-1"));
		else:
			// Check for UserName
			$getUsernameCount = $this->db->select()->where('username',$username)->from('userRegistration')->get()->num_rows();
			if($getUsernameCount > 0 ):
				// username Already Exists.
				return json_encode(array("userRegistration"=>"-3"));
			else:
				// can register
				$encryptedPassword = base64_encode($password);
				$this->db->insert("userRegistration",array("firstName"=>$firstName,"lastName"=>$lastName,"zipcode"=>$zipcode,"drinkerLevel"=>$drinkerLevel,"email"=>$email,"username"=>$username,"password"=>$encryptedPassword,"createdon"=>timeStamp,"updatedon"=>timeStamp));
				$userId = $this->db->insert_id();
				
				
				$userTaste = array(
								"aroma"=>3,
								"sweet"=>3,
								"bitter"=>3,
								"malt"=>3,
								"yeast"=>3,
								"mouthFeel"=>3,
								"sour"=>3,
								"additive"=>3,
								"booziness"=>3,
								"sour_status"=>1,
								"additive_status"=>1,
								"booziness_status"=>1,
								"yeast_status"=>1,
								"userId"=>$userId,
								"createdon"=>timeStamp,
								);
				$this->db->insert("userTaste",$userTaste);
				return json_encode(array("userRegistration"=>"$userId"));
			endif;
		endif;
	}
	
	/**************************************************************************************
	*									User Login										  *
	**************************************************************************************/
	
	Function userLogin($username,$password){
		$encryptedPassword = base64_encode($password);
		$userEmail = $this->db->select('id')->where(array('email'=>$username,"password"=>$encryptedPassword))->from('userRegistration')->get()->row_array();
		if($userEmail):
			// if userEmail Exists
			$checkUserName = $this->db->select('id')->where(array('username'=>$username,"password"=>$encryptedPassword))->from('userRegistration')->get()->row_array();
			
			if($checkUserName):
				return json_encode(array("userLogin"=>$checkUserName['id']));
			else:
				return json_encode(array("userLogin"=>"-1"));
			endif;
		else:
			$checkUserName = $this->db->select('id')->where(array('username'=>$username,"password"=>$encryptedPassword))->from('userRegistration')->get()->row_array();
			if($checkUserName):
				$beerProfile = $this->db->select('aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status')->where('userId',$checkUserName['id'])->from('userTaste')->get()->row_array();
				if($beerProfile):
					$beerArr = array($beerProfile['aroma'],$beerProfile['sweet'],$beerProfile['bitter'],$beerProfile['malt'],$beerProfile['yeast'],$beerProfile['mouthFeel'],$beerProfile['sour'],$beerProfile['additive'],$beerProfile['booziness'],$beerProfile['sour_status'],$beerProfile['additive_status'],$beerProfile['booziness_status'],$beerProfile['yeast_status']);
				else:
					$beerArr = array();
				endif;
				
				return json_encode(array("userLogin"=>$checkUserName['id'],"beerProfile"=>$beerArr));
			else:
				return json_encode(array("userLogin"=>"-1"));
			endif;
		endif;
	}
	
	/**************************************************************************************
	*									Forgot Password									  *
	**************************************************************************************/
	
	Function forgotPassword($username){
		$subject = 'Password recovery email.';
		
		$checkUserName = $this->db->select('password,email,username,firstName,lastName')->where('username',$username)->from('userRegistration')->get()->row_array();
		if($checkUserName):
			// Password will sent on user's Email address
			$firstName = $checkUserName['firstName'];
			$lastName = $checkUserName['lastName'];
			$email = $checkUserName['email'];
			$password = base64_decode($checkUserName['password']);
			
			$body = 'Dear ' .$firstName. ' '.$lastName.',
<br/><br/> Your password for Craft Beer Taste app is ' . $password . '<br/><br/>Thanks.<br>Craft Beer.';
			$this->sendUserEmail($email,$body,$subject);
			
			return json_encode(array("forgotPassword"=>"1"));
		else:
			// Check Email address
			$checkEmail = $this->db->select('password,email,username,firstName,lastName')->where('email',$username)->from('userRegistration')->get()->row_array();
			if($checkEmail):
				// Password will sent on user's Email address
				$firstName = $checkEmail['firstName'];
				$lastName = $checkEmail['lastName'];
				$email = $checkEmail['email'];
				$password = base64_decode($checkEmail['password']);
				
				$body = 'Dear ' .$firstName. ' '.$lastName.',
	<br/><br/> Your password for Craft Beer Taste app is ' . $password . '<br/><br/>Thanks.<br>Craft Beer.';
				$this->sendUserEmail($email,$body,$subject);
				
				return json_encode(array("forgotPassword"=>"1"));
			else:
				// Invalid User Name & Email Address
				return json_encode(array("forgotPassword"=>"-1"));
			endif;
		endif;
	}
	
	/***
	 **
	 **	Function to save user taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	function userTasteProfile($userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$sour_status,$additive_status,$booziness_status,$yeast_status){
		$validateUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validateUser > 0):
			$checkTaste = $this->db->select()->where('userId',$userId)->from('userTaste')->get()->num_rows();
			if($checkTaste > 0):
				return json_encode(array("userTasteProfile"=>"-1"));
			else:
				$userTaste = array(
								"aroma"=>$aroma,
								"sweet"=>$sweet,
								"bitter"=>$bitter,
								"malt"=>$malt,
								"yeast"=>$yeast,
								"mouthFeel"=>$mouthFeel,
								"sour"=>$sour,
								"additive"=>$additive,
								"booziness"=>$booziness,
								"sour_status"=>$sour_status,
								"additive_status"=>$additive_status,
								"booziness_status"=>$booziness_status,
								"yeast_status"=>$yeast_status,
								"userId"=>$userId,
								"createdon"=>timeStamp,
								);
				$this->db->insert("userTaste",$userTaste);
				return json_encode(array("userTasteProfile"=>"1"));
			endif;
		else:	
			return json_encode(array("userTasteProfile"=>"-3"));
		endif;
	}
	/***
	 **
	 **	Function to edit user taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	function editUserTasteProfile($userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$sour_status,$additive_status,$booziness_status,$yeast_status){
	
		$validateUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validateUser > 0):
			$checkTaste = $this->db->select()->where('userId',$userId)->from('userTaste')->get()->num_rows();
			if($checkTaste == 0):
				return json_encode(array("editUserTasteProfile"=>"-1"));
			else:
				$userTaste = array(
								"aroma"=>$aroma,
								"sweet"=>$sweet,
								"bitter"=>$bitter,
								"malt"=>$malt,
								"yeast"=>$yeast,
								"mouthFeel"=>$mouthFeel,
								"sour"=>$sour,
								"additive"=>$additive,
								"booziness"=>$booziness,
								"sour_status"=>$sour_status,
								"additive_status"=>$additive_status,
								"yeast_status"=>$yeast_status,
								"booziness_status"=>$booziness_status
								);
								//echo "<pre>";print_R($userTaste);die;
				$this->db->where("userId",$userId);
				$this->db->update("userTaste",$userTaste);
				return json_encode(array("editUserTasteProfile"=>"1"));
			endif;
		else:	
			return json_encode(array("editUserTasteProfile"=>"-3"));
		endif;
	}
	/***
	 **
	 **	Function to add user beer
	 ** @params brewery,beerName,beerStyle,abv,ibu,mood,venue,event,hype
	 **
	 ***/
	Function addUserBeer($userId,$brewery,$beerName,$beerStyle,$abv,$ibu,$mood,$venue,$event,$hype){
		$validateUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validateUser > 0):
			$validateBeer = $this->db->select()->where('beerName',$beerName)->from('userBeer')->get()->num_rows();
			if($validateBeer == 0):
				$userTaste = array(
								"brewery"=>$brewery,
								"beerName"=>$beerName,
								"beerStyle"=>$beerStyle,
								"abv"=>$abv,
								"ibu"=>$ibu,
								"mood"=>$mood,
								"venue"=>$venue,
								"event"=>$event,
								"hype"=>$hype,
								"userId"=>$userId,
								"createdon"=>timeStamp,
								);
				
				$this->db->insert("userBeer",$userTaste);
				$beerId = $this->db->insert_id();
				
				$checkBeer = $this->db->select('id')->where(array('beerName'=>$beerName,'breweryName'=>$brewery))->from('beerHistory')->get()->num_rows();
				if($checkBeer == 0):
					$this->db->insert('beerHistory',array("beerId"=>$beerId,"userId"=>$userId,"beerName"=>$beerName,"breweryName"=>$brewery,"createdon"=>timeStamp));
				endif;
				
				return json_encode(array("addUserBeer"=>$beerId));
			else:
				$validateBrewery = $this->db->select()->where('brewery',$brewery)->from('userBeer')->get()->num_rows();
				if($validateBrewery > 0 ):
					return json_encode(array("addUserBeer"=>"-4"));
				else:
					$userTaste = array(
								"brewery"=>$brewery,
								"beerName"=>$beerName,
								"beerStyle"=>$beerStyle,
								"abv"=>$abv,
								"ibu"=>$ibu,
								"mood"=>$mood,
								"venue"=>$venue,
								"event"=>$event,
								"hype"=>$hype,
								"userId"=>$userId,
								"createdon"=>timeStamp,
								);
					
					$this->db->insert("userBeer",$userTaste);
					$beerId = $this->db->insert_id();
					
					
					$checkBeer = $this->db->select('id')->where(array('beerName'=>$beerName,'breweryName'=>$brewery))->from('beerHistory')->get()->num_rows();
					if($checkBeer == 0):
						$this->db->insert('beerHistory',array("beerId"=>$beerId,"userId"=>$userId,"beerName"=>$beerName,"breweryName"=>$brewery,"createdon"=>timeStamp));
					endif;
					
					return json_encode(array("addUserBeer"=>$beerId));
				endif;
			endif;
		else:
			return json_encode(array("addUserBeer"=>"-3"));
		endif;
	}
	/***
	 **
	 **	Function to edit user beer
	 ** @params brewery,beerName,beerStyle,abv,ibu,mood,venue,event,hype
	 **
	 ***/
	
	Function editUserBeer($beerId,$userId,$brewery,$beerName,$beerStyle,$abv,$ibu,$mood,$venue,$event,$hype){
		$validateUser = $this->db->select()->where('id',$beerId)->from('userBeer')->get()->num_rows();
		if($validateUser > 0):
			
			$userTaste = array(
							"brewery"=>$brewery,
							"beerName"=>$beerName,
							"beerStyle"=>$beerStyle,
							"abv"=>$abv,
							"ibu"=>$ibu,
							"mood"=>$mood,
							"venue"=>$venue,
							"event"=>$event,
							"hype"=>$hype,
							);
			$this->db->where('id',$beerId);
			$this->db->update("userBeer",$userTaste);
			return json_encode(array("editUserBeer"=>"1"));
		else:
			return json_encode(array("editUserBeer"=>"-3"));
		endif;
	}
	/***
	 **
	 **	Function to add user beer taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	Function addBeerProfile($beerId,$userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$aroma_cmt,$sweet_cmt,$bitter_cmt,$malt_cmt,$yeast_cmt,$mouthFeel_cmt,$sour_cmt,$additive_cmt,$booziness_cmt,$booziness_cmt1,$mouthFeel_cmt1){
		$validateUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validateUser > 0):
			// validate beer 
			$validateBeer = $this->db->select()->where(array("id"=>$beerId,"userId"=>$userId))->from('userBeer')->get()->num_rows();
			if($validateBeer > 0):
				$beerProfile = array(
									"beerId"=>$beerId,
									"userId"=>$userId,
									"aroma"=>$aroma,
									"sweet"=>$sweet,
									"bitter"=>$bitter,
									"malt"=>$malt,
									"yeast"=>$yeast,
									"mouthFeel"=>$mouthFeel,
									"sour"=>$sour,
									"additive"=>$additive,
									"booziness"=>$booziness,
									"createdon"=>timeStamp,
									);
				$this->db->insert("beerProfile",$beerProfile);
				
				
				$beerProfileComment = array(
									"beerId"=>$beerId,
									"userId"=>$userId,
									"aroma_cmt"=>$aroma_cmt,
									"sweet_cmt"=>$sweet_cmt,
									"bitter_cmt"=>$bitter_cmt,
									"malt_cmt"=>$malt_cmt,
									"yeast_cmt"=>$yeast_cmt,
									"mouthFeel_cmt"=>$mouthFeel_cmt,
									"sour_cmt"=>$sour_cmt,
									"additive_cmt"=>$additive_cmt,
									"booziness_cmt"=>$booziness_cmt,
									"booziness_cmt1"=>$booziness_cmt1,
									"mouthFeel_cmt1"=>$mouthFeel_cmt1,
									"createdon"=>timeStamp,
									);
				$this->db->insert("beerProfileComment",$beerProfileComment);
				
				return json_encode(array("addBeerProfile"=>"1"));
			else:
				// in-valid beer
				return json_encode(array("addBeerProfile"=>"-1"));
			endif;
		else:
			return json_encode(array("addBeerProfile"=>"-3"));
		endif;
	}
	/***
	 **
	 **	Function to edit user beer taste profile
	 ** @params aroma,sweet,bitter,malt,yeast,mouthFeel,sour,additive,booziness,sour_status,additive_status,booziness_status,yeast_status
	 **
	 ***/
	Function editBeerProfile($beerId,$userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$aroma_cmt,$sweet_cmt,$bitter_cmt,$malt_cmt,$yeast_cmt,$mouthFeel_cmt,$sour_cmt,$additive_cmt,$booziness_cmt,$booziness_cmt1,$mouthFeel_cmt1){
		$validateUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validateUser > 0):
			// validate beer 
			$validateBeer = $this->db->select('brewery,beerName')->where(array("id"=>$beerId))->from('userBeer')->get()->row_array();
			if($validateBeer):
				$brewery = $validateBeer['brewery'];
				$beerName = $validateBeer['beerName'];
				$validateBeerProfile = $this->db->select()->where(array("beerId"=>$beerId))->from('beerProfile')->get()->num_rows();
				if($validateBeerProfile):
					$beerProfile = array(
										"userId"=>$userId,
										"aroma"=>$aroma,
										"sweet"=>$sweet,
										"bitter"=>$bitter,
										"malt"=>$malt,
										"yeast"=>$yeast,
										"mouthFeel"=>$mouthFeel,
										"sour"=>$sour,
										"additive"=>$additive,
										"booziness"=>$booziness,
										"createdon"=>timeStamp,
										);
					$this->db->where('beerId',$beerId);
					$this->db->update("beerProfile",$beerProfile);
					
					
					$beerProfileComment = array(
										"aroma_cmt"=>$aroma_cmt,
										"sweet_cmt"=>$sweet_cmt,
										"bitter_cmt"=>$bitter_cmt,
										"malt_cmt"=>$malt_cmt,
										"yeast_cmt"=>$yeast_cmt,
										"mouthFeel_cmt"=>$mouthFeel_cmt,
										"sour_cmt"=>$sour_cmt,
										"additive_cmt"=>$additive_cmt,
										"booziness_cmt"=>$booziness_cmt,
										"booziness_cmt1"=>$booziness_cmt1,
										"mouthFeel_cmt1"=>$mouthFeel_cmt1,
										"createdon"=>timeStamp,
										);
					$this->db->where('beerId',$beerId);
					$this->db->update("beerProfileComment",$beerProfileComment);
					
					
					$checkBeer = $this->db->select('id')->where(array('beerName'=>$beerName,'breweryName'=>$brewery))->from('beerHistory')->get()->num_rows();
					if($checkBeer == 0):
						$this->db->insert('beerHistory',array("beerId"=>$beerId,"userId"=>$userId,"beerName"=>$beerName,"breweryName"=>$brewery,"createdon"=>timeStamp));
					endif;
					
					
					
				else:
					$beerProfile = array(
										"beerId"=>$beerId,
										"aroma"=>$aroma,
										"sweet"=>$sweet,
										"bitter"=>$bitter,
										"malt"=>$malt,
										"yeast"=>$yeast,
										"mouthFeel"=>$mouthFeel,
										"sour"=>$sour,
										"additive"=>$additive,
										"booziness"=>$booziness,
										"createdon"=>timeStamp,
										);
					
					$this->db->insert("beerProfile",$beerProfile);
					
					
					$beerProfileComment = array(
										"beerId"=>$beerId,
										"aroma_cmt"=>$aroma_cmt,
										"sweet_cmt"=>$sweet_cmt,
										"bitter_cmt"=>$bitter_cmt,
										"malt_cmt"=>$malt_cmt,
										"yeast_cmt"=>$yeast_cmt,
										"mouthFeel_cmt"=>$mouthFeel_cmt,
										"sour_cmt"=>$sour_cmt,
										"additive_cmt"=>$additive_cmt,
										"booziness_cmt"=>$booziness_cmt,
										"booziness_cmt1"=>$booziness_cmt1,
										"mouthFeel_cmt1"=>$mouthFeel_cmt1,
										"createdon"=>timeStamp,
										);
					
					$this->db->insert("beerProfileComment",$beerProfileComment);
					
					$checkBeer = $this->db->select('id')->where(array('beerName'=>$beerName,'breweryName'=>$brewery))->from('beerHistory')->get()->num_rows();
					if($checkBeer == 0):
						$this->db->insert('beerHistory',array("beerId"=>$beerId,"userId"=>$userId,"beerName"=>$beerName,"breweryName"=>$brewery,"createdon"=>timeStamp));
					endif;
					
					
					
					
				endif;
				return json_encode(array("editBeerProfile"=>"1"));
			else:
				// in-valid beer
				return json_encode(array("editBeerProfile"=>"-1"));
			endif;
		else:
			return json_encode(array("editBeerProfile"=>"-3"));
		endif;
	}
	/***
	 **
	 **	Function to get Beer Profile correspond to beer Id
	 **
	 ***/
	Function getBeerProfile($beerId){
		$validateBeer = $this->db->select()->where('id',$beerId)->from('userBeer')->get()->row_array();
		if($validateBeer):
			$validateBeerProfile = $this->db->select()->where('id',$beerId)->from('beerProfile')->get()->row_array();
			if($validateBeerProfile):
				$this->db->select('beerProfile.aroma,beerProfile.sweet,beerProfile.bitter,beerProfile.malt,beerProfile.yeast,beerProfile.mouthFeel,beerProfile.sour,beerProfile.additive,beerProfile.booziness,userBeer.brewery,userBeer.beerName,userBeer.beerStyle,userBeer.abv,userBeer.ibu,userBeer.mood,userBeer.venue,userBeer.event,userBeer.hype');
				$this->db->from('beerProfile');
				$this->db->join('userBeer', 'userBeer.id = beerProfile.beerId', 'Left');
				$this->db->where('userBeer.id', $beerId); 
				$result = $this->db->get()->row_array();
				return json_encode(array("getBeerProfile"=>$result));
			else:
				return json_encode(array("getBeerProfile"=>array("aroma"=>"","sweet"=>"","bitter"=>"","malt"=>"","yeast"=>"","mouthFeel"=>"","sour"=>"","additive"=>"","booziness"=>"","brewery"=>$validateBeer['brewery'],"beerName"=>$validateBeer['beerName'],"beerStyle"=>$validateBeer['beerStyle'],"abv"=>$validateBeer['abv'],"ibu"=>$validateBeer['ibu'],"mood"=>$validateBeer['mood'],"venue"=>$validateBeer['venue'],"event"=>$validateBeer['event'],"hype"=>$validateBeer['hype'])));
			endif;
		else:
			return json_encode(array("getBeerProfile"=>"-1"));
		endif;
	}
	
	/***
	 **
	 **	Function to search beer either with brewery name or beer name
	 ** If Beer taste parameters is exactly matched with user taste profile then the beer percentage would be 100% if all the parameters of user taste is less/greater than 1 with beer taste profile then the percentage would be 66%. if all the parameters of user taste is less/greater than 2 with beer taste profile then the percentage would be 33%
	 **
	 ***/
	function searchBeer($userId,$beerName){
		
		
		$beerName =  mysql_real_escape_string($beerName);
		$validTasteProfile = $this->db->select()->where('userId',$userId)->from('userTaste')->get()->row_array();
	//	echo "My Taste ---- <pre>";print_R($validTasteProfile);echo "<br/>";
		$total = 9;
		$myTaste = 0;
		
		$bearArr = array();
		$where = "(beerName like '%".$beerName."%' || brewery like '%".$beerName."%' ) || (beerName like '%".$beerName."%' && brewery like '%".$beerName."%' )";
		$validateBeer = $this->db->select()->where($where)->from('userBeer')->order_by('beerName','ASC')->get()->result_array();
		
		if($validateBeer):
			foreach($validateBeer as $beer):
				$total = 9;
				$myTaste = 0;
				$tastePercentage = 0;
				
				$beerId = $beer['id'];
				
				
				if($beer['brewery']):
					$brewery = $beer['brewery'];
				else:
					$brewery = '';
				endif;
				if($beer['beerName']):
					$beerName = $beer['beerName'];
				else:
					$beerName = '';
				endif;
				if($beer['beerStyle']):
					$beerStyle = $beer['beerStyle'];
				else:
					$beerStyle = '';
				endif;
				if($beer['abv']):
					$abv = $beer['abv'];
				else:
					$abv = '';
				endif;
				if($beer['ibu']):
					$ibu = $beer['ibu'];
				else:
					$ibu = '';
				endif;
				if($beer['mood']):
					$mood = $beer['mood'];
				else:
					$mood = '';
				endif;
				if($beer['venue']):
					$venue = $beer['venue'];
				else:
					$venue = '';
				endif;
				if($beer['event']):
					$event = $beer['event'];
				else:
					$event = '';
				endif;
				if($beer['hype']):
					$hype = $beer['hype'];
				else:
					$hype = '';
				endif;
				
				$validateBeerProfile = $this->db->select()->where('beerId',$beerId)->from('beerProfile')->get()->row_array();

				if($validateBeerProfile):
				
					$query = $this->db->query("select s.userId,s.brewery,s.beerName,s.beerStyle,s.abv,s.ibu,s.mood,s.venue,s.event,s.hype,bp.aroma,bp.sweet,bp.bitter,bp.malt,bp.yeast,bp.mouthFeel,bp.sour,bp.additive,bp.booziness from userBeer s left join beerProfile bp on s.id=bp.beerId where s.id=$beerId");
					$result = $query->row_array();
					
					
					if($result['aroma'] ==$validTasteProfile['aroma'] ):
						$myTaste = $myTaste +1;
					elseif(($result['aroma'] ==($validTasteProfile['aroma']+1)) || ($result['aroma'] ==($validTasteProfile['aroma']-1))):
						$myTaste = $myTaste +0.66;
					elseif(($result['aroma'] ==($validTasteProfile['aroma']+2)) || ($result['aroma'] ==($validTasteProfile['aroma']-2))):
						$myTaste = $myTaste +0.33;
					endif;
					
					if($result['sweet'] ==($validTasteProfile['sweet'])):
						$myTaste = $myTaste +1;
					elseif(($result['sweet'] ==($validTasteProfile['sweet']+1)) || ($result['sweet'] ==($validTasteProfile['sweet']-1))):
						$myTaste = $myTaste +0.66;
					elseif(($result['sweet'] ==($validTasteProfile['sweet']+2)) || ($result['sweet'] ==($validTasteProfile['sweet']-2))):
						$myTaste = $myTaste +0.33;
					endif;
					
					if($result['bitter'] ==($validTasteProfile['bitter'])):
						$myTaste = $myTaste +1;
					elseif(($result['bitter'] ==($validTasteProfile['bitter']+1)) || ($result['bitter'] ==($validTasteProfile['bitter']-1))):
						$myTaste = $myTaste +0.66;
					elseif(($result['bitter'] ==($validTasteProfile['bitter']+2)) || ($result['bitter'] ==($validTasteProfile['bitter']-2))):
						$myTaste = $myTaste +0.33;
					endif;
					
					if($result['malt'] ==($validTasteProfile['malt'])):
						$myTaste = $myTaste +1;
					elseif(($result['malt'] ==($validTasteProfile['malt']+1)) || ($result['malt'] ==($validTasteProfile['malt']-1))):
						$myTaste = $myTaste +0.66;
					elseif(($result['malt'] ==($validTasteProfile['malt']+2)) || ($result['malt'] ==($validTasteProfile['malt']-2))):
						$myTaste = $myTaste +0.33;
					endif;
					
					if($result['yeast'] != 0):
						if($result['yeast'] ==($validTasteProfile['yeast'])):
							$myTaste = $myTaste +1;
						elseif(($result['yeast'] ==($validTasteProfile['yeast']+1)) || ($result['yeast'] ==($validTasteProfile['yeast']-1))):
							$myTaste = $myTaste +0.66;
						elseif(($result['yeast'] ==($validTasteProfile['yeast']+2)) || ($result['yeast'] ==($validTasteProfile['yeast']-2))):
							$myTaste = $myTaste +0.33;
						endif;
					else:	
						$total = $total - 1;
					endif;
					
					if($result['mouthFeel'] ==($validTasteProfile['mouthFeel'])):
						$myTaste = $myTaste +1;
					elseif(($result['mouthFeel'] ==($validTasteProfile['mouthFeel']+1)) || ($result['mouthFeel'] ==($validTasteProfile['mouthFeel']-1))):
						$myTaste = $myTaste +0.66;
					elseif(($result['mouthFeel'] ==($validTasteProfile['mouthFeel']+2)) || ($result['mouthFeel'] ==($validTasteProfile['mouthFeel']-2))):
						$myTaste = $myTaste +0.33;
					endif;
					
					if($result['sour'] != 0):
						if($result['sour'] ==($validTasteProfile['sour'])):
							$myTaste = $myTaste +1;
						elseif(($result['sour'] ==($validTasteProfile['sour']+1)) || ($result['sour'] ==($validTasteProfile['sour']-1))):
							$myTaste = $myTaste +0.66;
						elseif(($result['sour'] ==($validTasteProfile['sour']+2)) || ($result['sour'] ==($validTasteProfile['sour']-2))):
							$myTaste = $myTaste +0.33;
						endif;
					else:
						$total = $total - 1;
					endif;
					
					if($result['additive'] != 0):
						if($result['additive'] ==($validTasteProfile['additive'])):
							$myTaste = $myTaste +1;
						elseif(($result['additive'] ==($validTasteProfile['additive']+1)) || ($result['additive'] ==($validTasteProfile['additive']-1))):
							$myTaste = $myTaste +0.66;
						elseif(($result['additive'] ==($validTasteProfile['additive']+2)) || ($result['additive'] ==($validTasteProfile['additive']-2))):
							$myTaste = $myTaste +0.33;
						endif;
					else:
						$total = $total - 1;
					endif;
					
					if($result['booziness'] != 0):
						if($result['booziness'] ==($validTasteProfile['booziness'])):
							$myTaste = $myTaste +1;
						elseif(($result['booziness'] ==($validTasteProfile['booziness']+1)) || ($result['booziness'] ==($validTasteProfile['booziness']-1))):
							$myTaste = $myTaste +0.66;
						elseif(($result['booziness'] ==($validTasteProfile['booziness']+2)) || ($result['booziness'] ==($validTasteProfile['booziness']-2))):
							$myTaste = $myTaste +0.33;
						endif;
					else:
						$total = $total - 1;
					endif;
					
					
					if($total == 0):
						$tastePercentage = 0;
					else:
						$tastePercentage = $myTaste/$total  * 100;
						$tastePercentage = round($tastePercentage);
					endif;
					
					if($tastePercentage <= 6):
						$beerBottle = 0;
					endif;
					if($tastePercentage >= 6 && $tastePercentage <= 15 ):
						$beerBottle = 10;
					endif;
					if($tastePercentage >= 16 && $tastePercentage <= 25 ):
						$beerBottle = 20;
					endif;
					if($tastePercentage >= 26 && $tastePercentage <= 35 ):
						$beerBottle = 30;
					endif;
					if($tastePercentage >= 36 && $tastePercentage <= 45 ):
						$beerBottle = 40;
					endif;
					if($tastePercentage >= 46 && $tastePercentage <= 55 ):
						$beerBottle = 50;
					endif;
					if($tastePercentage >= 56 && $tastePercentage <= 65 ):
						$beerBottle = 60;
					endif;
					if($tastePercentage >= 66 && $tastePercentage <= 75 ):
						$beerBottle = 70;
					endif;
					if($tastePercentage >= 76 && $tastePercentage <= 85 ):
						$beerBottle = 80;
					endif;
					if($tastePercentage >= 86 && $tastePercentage <= 95 ):
						$beerBottle = 90;
					endif;
					if($tastePercentage >= 96):
						$beerBottle = 100;
					endif;
					
					$bearArr[] = array("beerId"=>$beerId,"aroma"=>$result['aroma'],"sweet"=>$result['sweet'],"bitter"=>$result['bitter'],"malt"=>$result['malt'],"yeast"=>$result['yeast'],"mouthFeel"=>$result['mouthFeel'],"sour"=>$result['sour'],"additive"=>$result['additive'],"booziness"=>$result['booziness'],"brewery"=>$brewery,"beerName"=>$beerName,"beerStyle"=>$beerStyle,"abv"=>$abv,"ibu"=>$ibu,"mood"=>$mood,"venue"=>$venue,"event"=>$event,"hype"=>$hype,"tastePercentage"=>$tastePercentage,"beerBottle"=>$beerBottle);
				else:
					$bearArr[] = array("beerId"=>$beerId,"aroma"=>"0","sweet"=>"0","bitter"=>"0","malt"=>"0","yeast"=>"0","mouthFeel"=>"0","sour"=>"0","additive"=>"0","booziness"=>"0","brewery"=>$brewery,"beerName"=>$beerName,"beerStyle"=>$beerStyle,"abv"=>$abv,"ibu"=>$ibu,"mood"=>$mood,"venue"=>$venue,"event"=>$event,"hype"=>$hype,"tastePercentage"=>0,"beerBottle"=>0);
				endif; 
			endforeach;
			//die;
			return json_encode(array("searchBeer"=>$bearArr));
		else:
			return json_encode(array("searchBeer"=>$bearArr));
		endif;
	}
	/***
	 **
	 ** function to get user taste profile 
	 **
	 ***/
	function getUserTasteProfile($userId){
		$validUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validUser > 0):
			$validTasteProfile = $this->db->select()->where('userId',$userId)->from('userTaste')->get()->row_array();
			if($validTasteProfile):
				return json_encode(array("getUserTasteProfile"=>$validTasteProfile));
			else:
				return json_encode(array("getUserTasteProfile"=>"-1"));
			endif;
		else:
			return json_encode(array("getUserTasteProfile"=>"-3"));
		endif;
	}
	/***
	 **
	 ** function to get user Profile
	 **
	 ***/
	function userProfile($userId,$type){
		$uProfileArr = array();
		$uProfile = $this->db->select('firstName,lastName,zipcode,drinkerLevel,email,username')->where('id',$userId)->from('userRegistration')->get()->row_array();
		if($type ==1):
			$uProfileArr[] = array("firstName"=>$uProfile['firstName'],"lastName"=>$uProfile['lastName'],"zipcode"=>$uProfile['zipcode'],"drinkerLevel"=>$uProfile['drinkerLevel'],"email"=>$uProfile['email'],"username"=>$uProfile['username']);
			return json_encode(array("userProfile"=>$uProfileArr));
		else:
			return json_encode(array("userProfile"=>$uProfile));
		endif;
	}
	/***
	 **
	 ** function to edit user Profile
	 **
	 ***/
	
	function editUserProfile($userId,$firstName,$lastName,$zipcode,$drinkerLevel,$email,$username){
		$uProfile =$this->db->select()->where('id',$userId)->from('userRegistration')->get()->row_array();
		if($uProfile):
			$validateUsername =$this->db->select('id')->where("username",$username)->from('userRegistration')->get()->row_array();
			if($validateUsername['id'] == $userId):
				$validateEmail =$this->db->select('id')->where("email",$email)->from('userRegistration')->get()->row_array();
				if($validateUsername['id'] == $userId):
					$this->db->where('id',$userId);
					$this->db->update("userRegistration",array("firstName"=>$firstName,"lastName"=>$lastName,"zipcode"=>$zipcode,"drinkerLevel"=>$drinkerLevel,"email"=>$email,"username"=>$username));
					return json_encode(array("editUserProfile"=>"1"));				
				else:
					// email address exists
					return json_encode(array("editUserProfile"=>"-4"));				
				endif;
			else:
				// username exists
				return json_encode(array("editUserProfile"=>"-3"));
			endif;
		else:
			return json_encode(array("editUserProfile"=>"-1"));
		endif;
	}
	
	/**
	 *	Function to change User Profile Password.
	 **/
	function changePassword($userId,$password,$newPassword){
		$uProfile =$this->db->select()->where('id',$userId)->from('userRegistration')->get()->row_array();
		if($uProfile):
			// User has valid Profile
			$encryPwd = base64_encode($password); //encrypt user Password
			// validate user password with his/her profile
			$validatePassword =$this->db->select()->where(array('id'=>$userId,'password'=>$encryPwd))->from('userRegistration')->get()->row_array();
			if($validatePassword):
				// if user password is matched with his/her profile
				if($password == $newPassword):
				// Old and new password is same but old password matches with his profile
					return json_encode(array("changePassword"=>"-4"));
				else:
					//Password updated Successfully
					$this->db->where('id',$userId);
					$this->db->update('userRegistration',array('password'=>base64_encode($newPassword)));
					return json_encode(array("changePassword"=>"1"));
				endif;
			else:
				// Old Password doesn't match with his/her profile
				return json_encode(array("changePassword"=>"-3"));
			endif;
		else:
			// User doesn't Exists
			return json_encode(array("changePassword"=>"-1"));
		endif;
	}
	
	/**************************************************************************************
	*									Email To User									  *
	**************************************************************************************/
	
	function sendUserEmail($email,$body,$subject){
		// Header of Mail.
		$headers = "From: " . 'Craft Beer' . "<" . 'pankaj.arora@netsmartz.net' . ">\n";
		$headers .= "Reply-To: <" . 'pankaj.arora@netsmartz.net' . ">\n";
		$headers .= "MIME-Version: 1.0\n";
		$headers .= "X-Mailer: PHP5\n";
		$headers .= "X-Priority: 3\n";
		//1 = Urgent, 3 = Normal
		$headers .= "Content-Type: text/html; charset=\"iso-8859-1\"\n";
		mail($email,$subject, $body, $headers);
		return true;
	}
	
	function beerHistory($userId){
		$validateUser = $this->db->select()->where('id',$userId)->from('userRegistration')->get()->num_rows();
		if($validateUser >0):
			$beers = $this->db->select('beerId,beerName,breweryName')->where('userId',$userId)->from('beerHistory')->get()->result_array();
			
			return json_encode(array("beerHistory"=>$beers));
		else:
			return json_encode(array("beerHistory"=>"-1"));
		endif;
	
	}
}