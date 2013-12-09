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
		$activityName = 'editUserBeer:';
		log_message('info', "{$activityName} beerId={$beerId}, userId={$userId}");
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
	
	Function addBeerProfile($beerId,$userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$aroma_cmt,$sweet_cmt,$bitter_cmt,$malt_cmt,$yeast_cmt,$mouthFeel_cmt,$sour_cmt,$additive_cmt,$booziness_cmt,$booziness_cmt1,$mouthFeel_cmt1){
		$activityName = 'addBeerProfile:';
		log_message('info', "{$activityName} beerId={$beerId}, userId={$userId}");
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
	
	Function editBeerProfile($beerId,$userId,$aroma,$sweet,$bitter,$malt,$yeast,$mouthFeel,$sour,$additive,$booziness,$aroma_cmt,$sweet_cmt,$bitter_cmt,$malt_cmt,$yeast_cmt,$mouthFeel_cmt,$sour_cmt,$additive_cmt,$booziness_cmt,$booziness_cmt1,$mouthFeel_cmt1){
		$activityName = 'editBeerProfile:';
		log_message('info', "{$activityName} beerID={$beerId}, userId={$userId}");
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
	
	Function getBeerProfile($beerId){
		$activityName = 'getBeerProfile:';
		log_message('info', "{$activityName} beerId={$beerId}");
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
	 ** If Beer taste parameters is exactly matched with user taste profile 
	 then the beer percentage would be 100% if all the parameters of user taste is less/greater than 1 with beer taste profile then the percentage would be 66%. 
	 if all the parameters of user taste is less/greater than 2 with beer taste profile then the percentage would be 33%
	 **
	 ***/
	function searchBeer($userId,$beerName){
		$activityName = 'searchBeer:';
		log_message('info', "{$activityName} beerName={$beerName}, userId={$userId}");
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
				//echo "<pre>";print_r($validateBeerProfile);
				if($validateBeerProfile):
				
					$query = $this->db->query("select s.userId,s.brewery,s.beerName,s.beerStyle,s.abv,s.ibu,s.mood,s.venue,s.event,s.hype,bp.aroma,bp.sweet,bp.bitter,bp.malt,bp.yeast,bp.mouthFeel,bp.sour,bp.additive,bp.booziness from userBeer s left join beerProfile bp on s.id=bp.beerId where s.id=$beerId");
					$result = $query->row_array();
					
					/* if(($result['aroma'] ==($validTasteProfile['aroma'])) || ($result['aroma'] ==($validTasteProfile['aroma']+1)) || ($result['aroma'] ==($validTasteProfile['aroma']-1))):
						
						$myTaste = $myTaste +1;
					endif;
					if(($result['sweet'] ==($validTasteProfile['sweet'])) || ($result['sweet'] ==($validTasteProfile['sweet']+1)) || ($result['sweet'] ==($validTasteProfile['sweet']-1))):
						$myTaste = $myTaste +1;
					endif;
					if(($result['bitter'] ==($validTasteProfile['bitter'])) || ($result['bitter'] ==($validTasteProfile['bitter']+1)) || ($result['bitter'] ==($validTasteProfile['bitter']-1))):
						
						$myTaste = $myTaste +1;
					endif;
					if(($result['malt'] ==($validTasteProfile['malt'])) || ($result['malt'] ==($validTasteProfile['malt']+1)) || ($result['malt'] ==($validTasteProfile['malt']-1))):
						$myTaste = $myTaste +1;
					endif;
					
					if($result['yeast'] != 0):
						if(($result['yeast'] ==($validTasteProfile['yeast'])) || ($result['yeast'] ==($validTasteProfile['yeast']+1)) || ($result['yeast'] ==($validTasteProfile['yeast']-1))):
							$myTaste = $myTaste +1;
						endif;
					else:	
						$total = $total - 1;
					endif;
					if(($result['mouthFeel'] ==($validTasteProfile['mouthFeel'])) || ($result['mouthFeel'] ==($validTasteProfile['mouthFeel']+1)) || ($result['mouthFeel'] ==($validTasteProfile['mouthFeel']-1))):
						$myTaste = $myTaste +1;
					endif;
					
					if($result['sour'] != 0):
						if(($result['sour'] ==($validTasteProfile['sour'])) || ($result['sour'] ==($validTasteProfile['sour']+1)) || ($result['sour'] ==($validTasteProfile['sour']-1))):
							$myTaste = $myTaste +1;
						endif;
					else:
						$total = $total - 1;
					endif;
					
					if($result['additive'] != 0):
						if(($result['additive'] ==($validTasteProfile['additive'])) || ($result['additive'] ==($validTasteProfile['additive']+1)) || ($result['additive'] ==($validTasteProfile['additive']-1))):
							$myTaste = $myTaste +1;
						endif;
					else:
						$total = $total - 1;
					endif;
					
					if($result['booziness'] != 0):
						if(($result['booziness'] ==($validTasteProfile['booziness'])) || ($result['booziness'] ==($validTasteProfile['booziness']+1)) || ($result['booziness'] ==($validTasteProfile['booziness']-1))):
							$myTaste = $myTaste +1;
						endif;
					else:
						$total = $total - 1;
					endif; */
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
                                        
                                        
                                        
                                        //add facebook twitter parameter
                                        $FacebookData = $this->db->select()->where('beerId',$beerId)->where('networkId','1')->from('socialMediaAcct')->get()->row_array();
                                        if($FacebookData):
                                            $facebook = isset($FacebookData['handle'])?$FacebookData['handle']:'';
                                            $facebookurl = isset($FacebookData['url'])?$FacebookData['url']:'';
                                        else:
                                            $facebook = '';
                                            $facebookurl = '';
                                        endif;

                                        $twitterData = $this->db->select()->where('beerId',$beerId)->where('networkId','2')->from('socialMediaAcct')->get()->row_array();
                                        if($twitterData):
                                            $twitter = $twitterData['handle'];
                                        else:
                                            $twitter = '';
                                        endif;
                                        //facebook twitter parameter end
					
					//echo $total;
					//echo $myTaste."-----My Taste------ Total------".$total."<br/>";
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
					
					
					
					$bearArr[] = array("beerId"=>$beerId,"aroma"=>$result['aroma'],"sweet"=>$result['sweet'],"bitter"=>$result['bitter'],"malt"=>$result['malt'],"yeast"=>$result['yeast'],"mouthFeel"=>$result['mouthFeel'],"sour"=>$result['sour'],"additive"=>$result['additive'],"booziness"=>$result['booziness'],"brewery"=>$brewery,"beerName"=>$beerName,"beerStyle"=>$beerStyle,"abv"=>$abv,"ibu"=>$ibu,"mood"=>$mood,"venue"=>$venue,"event"=>$event,"hype"=>$hype,"tastePercentage"=>$tastePercentage,"beerBottle"=>$beerBottle,"facebook"=>$facebook,"facebookurl "=>$facebookurl,"twitter"=>$twitter);
				else:
					$bearArr[] = array("beerId"=>$beerId,"aroma"=>"0","sweet"=>"0","bitter"=>"0","malt"=>"0","yeast"=>"0","mouthFeel"=>"0","sour"=>"0","additive"=>"0","booziness"=>"0","brewery"=>$brewery,"beerName"=>$beerName,"beerStyle"=>$beerStyle,"abv"=>$abv,"ibu"=>$ibu,"mood"=>$mood,"venue"=>$venue,"event"=>$event,"hype"=>$hype,"tastePercentage"=>0,"beerBottle"=>0,"facebook"=>"","facebookurl "=>"","twitter"=>"");
				endif; 
			endforeach;
			//die;
			return json_encode(array("searchBeer"=>$bearArr));
		else:
			return json_encode(array("searchBeer"=>$bearArr));
		endif;
	}
	
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


    // BreweryDBWebHooksInsert function insert data into brewerydata table when get data from web hooks
    function BreweryDBWebHooksInsert($attribute,$attributeId,$action,$subAction,$subAttributeId,$timestamp,$post_data){

          $get_brewery_data = $this->db->select()->where('attribute',$attribute)->where('attributeId',$attributeId)->where('action',$action)->where('subAttributeId',$subAttributeId)->where('subAction',$subAction)->where('timestamp',$timestamp)->from('brewerydata')->get()->result_array();
          if($get_brewery_data){
            return false;
          }
          else{
            $brewerydata_insert = '';
            $brewerydata_insert['attribute'] = $attribute;
            $brewerydata_insert['attributeId'] = $attributeId;
            $brewerydata_insert['action'] = $action;
            $brewerydata_insert['subAttributeId'] = $subAttributeId;
            $brewerydata_insert['timestamp'] = $timestamp;
            $brewerydata_insert['post_data'] = $post_data;

            if($subAction){
                  $brewerydata_insert['subAction'] = $subAction;
            }
            $this->db->insert("brewerydata",$brewerydata_insert);
            $inserted_id = $this->db->insert_id();
            return $inserted_id;
          }


    }

    //InsertUpdateBeerBrewery function insert update data for beerbrewery table
    function InsertUpdateBeerBrewery($beerdata,$subAttributeId,$timestamp,$subAction_typeAction,$apiKey){
    	$activityName = "InsertUpdateBeerBrewery";

        $breweries = isset($beerdata['breweries'])?$beerdata['breweries']:'';
        if($breweries){
            $brewery = '';
            $i = 0;
            foreach($breweries AS $brewery){
                if($subAttributeId == $brewery['id']){
                  $insert_brewey['name'] = $brewery['name'];
                  $insert_brewey['description'] = isset($brewery['description'])?$brewery['description']:'';
                  $insert_brewey['website'] = isset($brewery['website'])?$brewery['website']:'';
                  $insert_brewey['established'] = isset($brewery['established'])?$brewery['established']:'';
                  $insert_brewey['mediumImage'] = isset($brewery['images']['medium'])?$brewery['images']['medium']:'';
                  $insert_brewey['largeImage'] = isset($brewery['images']['large'])?$brewery['images']['large']:'';
                  $insert_brewey['icon'] = isset($brewery['images']['icon'])?$brewery['images']['icon']:'';
                  $insert_brewey['mailingListUrl'] = isset($brewery['mailingListUrl'])?$brewery['mailingListUrl']:'';
                  $insert_brewey['dateCreated'] =  $brewery['createDate'];
                }
                $insert_brewey_all[$i]['name'] = $brewery['name'];
                $insert_brewey_all[$i]['description'] = isset($brewery['description'])?$brewery['description']:'';
                $insert_brewey_all[$i]['website'] = isset($brewery['website'])?$brewery['website']:'';
                $insert_brewey_all[$i]['established'] = isset($brewery['established'])?$brewery['established']:'';
                $insert_brewey_all[$i]['mediumImage'] = isset($brewery['images']['medium'])?$brewery['images']['medium']:'';
                $insert_brewey_all[$i]['largeImage'] = isset($brewery['images']['large'])?$brewery['images']['large']:'';
                $insert_brewey_all[$i]['icon'] = isset($brewery['images']['icon'])?$brewery['images']['icon']:'';
                $insert_brewey_all[$i]['mailingListUrl'] = isset($brewery['mailingListUrl'])?$brewery['mailingListUrl']:'';
                $insert_brewey_all[$i]['dateCreated'] =  $brewery['createDate'];
            }
        }

        //create social media account from response
        $socialAccounts = isset($beerdata['socialAccounts'])?$beerdata['socialAccounts']:'';
        if($socialAccounts){
            $i = 0;
            $socialAccount = '';
            foreach($socialAccounts AS $socialAccount){
                $insert_socialacc[$i]['id'] = $socialAccount['id'];
                $insert_socialacc[$i]['network'] = isset($socialAccount['socialMedia']['name'])?$socialAccount['socialMedia']['name']:'';
                $insert_socialacc[$i]['networkId'] = isset($socialAccount['socialMedia']['id'])?$socialAccount['socialMedia']['id']:'';
                $insert_socialacc[$i]['url'] = isset($socialAccount['link'])?$socialAccount['link']:'';
                $insert_socialacc[$i]['handle'] = isset($socialAccount['handle'])?$socialAccount['handle']:'';
                $insert_socialacc[$i]['handle'] = isset($socialAccount['handle'])?$socialAccount['handle']:'';
                $i++;
            }
        }


        //create beer ingredient
        $url_ingredient =  'http://api.brewerydb.com/v2/beer/'.$beerdata['id'].'/ingredients/?key='.$apiKey.'&format=php';
        $result_ingredient = $this->webservicemodel->get_web_page($url_ingredient);
        if($result_ingredient['message'] == 'Request Successful'){
            if(isset($result_ingredient['data'])){
              $ingredients = $result_ingredient['data'];
              if($ingredients){
                  $i = 0;
                  $ingredient = '';
                  foreach($ingredients AS $ingredient){
                      $insert_ingredient[$i]['id'] = $ingredient['id'];
                      $insert_ingredient[$i]['name'] = isset($ingredient['name'])?$ingredient['name']:'';
                      $insert_ingredient[$i]['category'] = isset($ingredient['category'])?$ingredient['category']:'';
                      $insert_ingredient[$i]['categoryDisplay'] = isset($ingredient['categoryDisplay'])?$ingredient['categoryDisplay']:'';
                      $insert_ingredient[$i]['dateCreated'] = $ingredient['createDate'];
                      $i++;
                  }
              }
            }

        }
        $masterbeerId = $beerdata['id'];
        $get_beerId = $this->db->select()->where('masterBeerId',$masterbeerId)->from('beer')->get()->row_array();

        if($get_beerId){
            $beerId = $get_beerId['id'];
            $validateBeer = $this->db->select()->where('id',$beerId)->from('userBeer')->get()->row_array();
            if($validateBeer){
                $userBeerId = $validateBeer['id'];
                $update_breweryName['brewery'] = $insert_brewey['name'];
                $this->db->where('id',$userBeerId);
  		        $this->db->update("userBeer",$update_breweryName);
  		        log_message('debug', "{$activityName} updated userBeer record with id={$userBeerId} with data: " . implode_with_key($update_breweryName,'>',','));
            }
        }else{
            $beerName  = trim($beerdata['name']);
            $breweryName = isset($beerdata['breweries']['0']['name'])?$beerdata['breweries']['0']['name']:'';
            $brewery = trim($breweryName);
            $validateBeer = $this->db->select()->where('beerName',$beerName)->where('brewery',$brewery)->from('userBeer')->get()->row_array();

            if($validateBeer){
                $userBeerId = $validateBeer['id'];
                $update_breweryName['brewery'] = $insert_brewey['name'];
                $this->db->where('id',$userBeerId);
  		        $this->db->update("userBeer",$update_breweryName);
  		        log_message('debug', "{$activityName} updated userBeer record with id={$userBeerId} with data: " . implode_with_key($update_breweryName,'>',','));


                $insert_beer['name'] = $beerdata['name'];
                $insert_beer['abv'] = isset($beerdata['abv'])?$beerdata['abv']:'';
                $insert_beer['ibu'] = isset($beerdata['ibu'])?$beerdata['ibu']:'';
                $insert_beer['style'] = isset($beerdata['style']['name'])?$beerdata['style']['name']:'';
                $insert_beer['description'] = isset($beerdata['description'])?$beerdata['description']:'';
                $insert_beer['mediumLabel'] = isset($beerdata['labels']['medium'])?$beerdata['labels']['medium']:'';
                $insert_beer['largeLabel'] = isset($beerdata['labels']['large'])?$beerdata['labels']['large']:'';
                $insert_beer['icon'] = isset($beerdata['labels']['icon'])?$beerdata['labels']['icon']:'';
                $insert_beer['id'] = $userBeerId;
                $insert_beer['datecreated '] = $beerdata['createDate'];
                $insert_beer['masterBeerId'] = $beerdata['id'];
                //insert intp beer table
                $this->db->insert("beer",$insert_beer);

                log_message('debug', "{$activityName} inserted Beer record with id={$userBeerId} with data: " . implode_with_key($insert_beer,'>',','));

                if(isset($insert_brewey_all)){
                  foreach($insert_brewey_all AS $insert_beerbrewery_all ){
                      $insert_beerbrewery_all['beerId'] = $userBeerId;
                      $insert_beerbrewery_all['masterBreweryId'] = $subAttributeId;
                      $this->db->insert("beerBrewery",$insert_beerbrewery_all);

                      log_message('debug', "{$activityName} Inserted brewery record into BeerBrewery with value ".$this->webservicemodel->implode_with_key($insert_beerbrewery_all,'>',','));

                  }
                }
                else
                {
                	log_message('debug', "{$activityName} Beer had no Brewery data to insert into BeerBrewery table");

                }

                //insert social accounts
                if(isset($insert_socialacc)){
                  foreach($insert_socialacc AS $insert_beersocialacc ){
                        $insert_beersocialacc['beerId'] = $userBeerId;
                        $this->db->insert("socialMediaAcct",$insert_beersocialacc);

                        log_message('debug', "{$activityName} Inserted socialMedia record into socialMediaAcct with value ".$this->webservicemodel->implode_with_key($insert_beersocialacc,'>',','));

                    }
                }
                else
                {
                	log_message('debug', "{$activityName} Beer had no Social account data to insert into socialMediaAcct table");

                }


                //insert ingredients
                if(isset($insert_ingredient)){
                  foreach($insert_ingredient AS $insert_beeringredient ){
                        $insert_beeringredient['beerId'] = $userBeerId;
                        $this->db->insert("beerIngredient",$insert_beeringredient);

                        log_message('debug', "{$activityName} Inserted ingredient record into beerIngredient with value ".$this->webservicemodel->implode_with_key($insert_beeringredient,'>',','));

                    }
                }
                else
                {
                	log_message('debug', "{$activityName} Beer had no ingredient data to insert into beerIngredient table");

                }

            }
        }
        $get_brewery = $this->db->select()->where('masterBreweryId',$subAttributeId)->where('beerId',$userBeerId)->from('beerBrewery')->get()->row_array();
        if($get_brewery){
          $breweryId = $get_brewery['id'];
          $this->db->where('id',$breweryId);
          $this->db->update("beerBrewery",$insert_brewey);
          log_message('debug', "{$activityName} updated beerBrewery record with id={$breweryId} with data: " . implode_with_key($insert_brewey,'>',','));
        }else{
          $insert_brewey['masterBreweryId'] = $subAttributeId;
          //$insert_brewey['dateCreated'] =  date("Y-m-d H:i:s", $timestamp);
          $insert_brewey['beerId'] = $userBeerId;
          $this->db->insert("beerBrewery",$insert_brewey);
          log_message('debug', "{$activityName} inserted beerBrewery record with data: " . implode_with_key($insert_brewey,'>',','));
        }

        log_message('debug',"{$activityName} completed execution");
    }

    //InsertIngredient function insert data into beeringredient table
    function InsertIngredient($ingredientdata,$attributeId,$subAttributeId,$timestamp){
    	$activityName = "InsertIngredient:";

        $get_beerId = $this->db->select()->where('masterBeerId',$attributeId)->from('beer')->get()->row_array();
        if($get_beerId){
          $beerId = $get_beerId['id'];
          foreach($ingredientdata AS $ingredient){
            if($subAttributeId == $ingredient['id']){
                $ingredient_data['id'] = $subAttributeId;
                $ingredient_data['beerId'] = $beerId;
                $ingredient_data['name'] = isset($ingredient['name'])?$ingredient['name']:'';
                $ingredient_data['category'] = isset($ingredient['category'])?$ingredient['category']:'';
                $ingredient_data['categoryDisplay'] = isset($ingredient['categoryDisplay'])?$ingredient['categoryDisplay']:'';
                $ingredient_data['dateCreated'] = $ingredient['createDate'];
                $this->db->insert("beerIngredient",$ingredient_data);

                log_message('debug', "{$activityName} inserted beerIngredient record with data: " . implode_with_key($ingredient_data,'>',','));
            }
          }
        }

        log_message('debug',"${activityName} completed execution");
    }

    //DeleteIngredient function delete data from beeringredient table
    function DeleteIngredient($attributeId,$subAttributeId){
    	$activityName = "DeleteIngredient:";

        $get_beerId = $this->db->select()->where('masterBeerId',$attributeId)->from('beer')->get()->row_array();
        if($get_beerId){
          $beerId = $get_beerId['id'];
          $this->db->where('id', $subAttributeId)->where('beerId ', $beerId);
          $this->db->delete('beerIngredient');
          log_message('debug', "{$activityName} deleted Ingredient with id={$subAttributeId} and beerId={$beerId}");
        }
    }

    //InsertUpdateSocialMediaAcct function insert-update data for socialmediaacc table
    function InsertUpdateSocialMediaAcct($beerdata,$subAttributeId,$subAction_typeAction,$timestamp,$apiKey){
    	$activityName = "InsertUpdateSocialMediaAcct:";
    	log_message('debug', "{$activityName} begin processing of {$subAction_typeAction}");
        //create beer brewery from response
        $breweries = isset($beerdata['breweries'])?$beerdata['breweries']:'';
        if($breweries){
            $i = 0;
            $brewery = '';
            foreach($breweries AS $brewery){
                $insert_brewey[$i]['name'] = $brewery['name'];
                $insert_brewey[$i]['masterBreweryId'] = $brewery['id'];
                $insert_brewey[$i]['description'] = isset($brewery['description'])?$brewery['description']:'';
                $insert_brewey[$i]['website'] = isset($brewery['website'])?$brewery['website']:'';
                $insert_brewey[$i]['established'] = isset($brewery['established'])?$brewery['established']:'';
                $insert_brewey[$i]['mediumImage'] = isset($brewery['images']['medium'])?$brewery['images']['medium']:'';
                $insert_brewey[$i]['largeImage'] = isset($brewery['images']['large'])?$brewery['images']['large']:'';
                $insert_brewey[$i]['icon'] = isset($brewery['images']['icon'])?$brewery['images']['icon']:'';
                $insert_brewey[$i]['mailingListUrl'] = isset($brewery['mailingListUrl'])?$brewery['mailingListUrl']:'';
                $insert_brewey[$i]['dateCreated'] =  $brewery['createDate'];
                $i++;
            }
        }
        //create social media account response
        $socialAccounts = isset($beerdata['socialAccounts'])?$beerdata['socialAccounts']:'';
        if($socialAccounts){
            $socialAccount = '';
            $i = 0;
            foreach($socialAccounts AS $socialAccount){
                if($subAttributeId == $socialAccount['id']){
                  $socialacc_data['network'] = isset($socialAccount['socialMedia']['name'])?$socialAccount['socialMedia']['name']:'';
                  $socialacc_data['networkId'] = isset($socialAccount['socialMedia']['id'])?$socialAccount['socialMedia']['id']:'';
                  $socialacc_data['url'] = isset($socialAccount['link'])?$socialAccount['link']:'';
                  $socialacc_data['handle'] = isset($socialAccount['handle'])?$socialAccount['handle']:'';
                }
                $socialacc_data_all[$i]['id'] = isset($socialAccount['id'])?$socialAccount['id']:'';
                $socialacc_data_all[$i]['network'] = isset($socialAccount['socialMedia']['name'])?$socialAccount['socialMedia']['name']:'';
                $socialacc_data_all[$i]['networkId'] = isset($socialAccount['socialMedia']['id'])?$socialAccount['socialMedia']['id']:'';
                $socialacc_data_all[$i]['url'] = isset($socialAccount['link'])?$socialAccount['link']:'';
                $socialacc_data_all[$i]['handle'] = isset($socialAccount['handle'])?$socialAccount['handle']:'';
            }
        }

        //create beer ingredient
        $url_ingredient =  'http://api.brewerydb.com/v2/beer/'.$beerdata['id'].'/ingredients/?key='.$apiKey.'&format=php';
        $result_ingredient = $this->webservicemodel->get_web_page($url_ingredient);
        if($result_ingredient['message'] == 'Request Successful'){
            if(isset($result_ingredient['data'])){
              $ingredients = $result_ingredient['data'];
              if($ingredients){
                  $i = 0;
                  $ingredient = '';
                  foreach($ingredients AS $ingredient){
                      $insert_ingredient[$i]['id'] = $ingredient['id'];
                      $insert_ingredient[$i]['name'] = isset($ingredient['name'])?$ingredient['name']:'';
                      $insert_ingredient[$i]['category'] = isset($ingredient['category'])?$ingredient['category']:'';
                      $insert_ingredient[$i]['categoryDisplay'] = isset($ingredient['categoryDisplay'])?$ingredient['categoryDisplay']:'';
                      $insert_ingredient[$i]['dateCreated'] = $ingredient['createDate'];
                      $i++;
                  }
              }
            }
        }


        $masterbeerId = $beerdata['id'];
        $get_beerId = $this->db->select()->where('masterBeerId',$masterbeerId)->from('beer')->get()->row_array();
        if($get_beerId){
            $beerId = $get_beerId['id'];

        }else{
            $beerName  = trim($beerdata['name']);
            $breweryName = isset($beerdata['breweries']['0']['name'])?$beerdata['breweries']['0']['name']:'';
            $brewery = trim($breweryName);
            $validateBeer = $this->db->select()->where('beerName',$beerName)->where('brewery',$brewery)->from('userBeer')->get()->row_array();
            if($validateBeer){
                $beerId = $validateBeer['id'];
                $update_breweryName['brewery'] = isset($beerdata['breweries']['0']['name'])?$beerdata['breweries']['0']['name']:'';
                $this->db->where('id',$beerId);
  		        $this->db->update("userBeer",$update_breweryName);

  		        log_message('debug', "{$activityName} Updated userBeer record with id={$beerId} with values ".$this->webservicemodel->implode_with_key($update_breweryName,'>',','));


                $insert_beer['name'] = $beerdata['name'];
                $insert_beer['abv'] = isset($beerdata['abv'])?$beerdata['abv']:'';
                $insert_beer['ibu'] = isset($beerdata['ibu'])?$beerdata['ibu']:'';
                $insert_beer['style'] = isset($beerdata['style']['name'])?$beerdata['style']['name']:'';
                $insert_beer['description'] = isset($beerdata['description'])?$beerdata['description']:'';
                $insert_beer['mediumLabel'] = isset($beerdata['labels']['medium'])?$beerdata['labels']['medium']:'';
                $insert_beer['largeLabel'] = isset($beerdata['labels']['large'])?$beerdata['labels']['large']:'';
                $insert_beer['icon'] = isset($beerdata['labels']['icon'])?$beerdata['labels']['icon']:'';
                $insert_beer['id'] = $beerId;
                $insert_beer['datecreated '] = $beerdata['createDate'];
                $insert_beer['masterBeerId'] = $beerdata['id'];
                //insert intp beer table
                $this->db->insert("beer",$insert_beer);

                log_message('debug', "{$activityName} Inserted record into beer table with values ".$this->webservicemodel->implode_with_key($insert_beer,'>',','));

                if(isset($insert_brewey)){
                  foreach($insert_brewey AS $insert_beerbrewery ){
                      $insert_beerbrewery['beerId'] = $beerId;
                      $this->db->insert("beerBrewery",$insert_beerbrewery);
                      log_message('debug', "{$activityName} Inserted brewery record into BeerBrewery with value ".$this->webservicemodel->implode_with_key($insert_beerbrewery,'>',','));
                  }
                }
                else
                {
                	log_message('debug', "{$activityName} Beer had no Brewery data to insert into BeerBrewery table");
                }
                //insert social accounts
                if(isset($socialacc_data_all)){
                  foreach($socialacc_data_all AS $socialacc_beerdata_all ){
                        $socialacc_beerdata_all['beerId'] = $beerId;
                        $this->db->insert("socialMediaAcct",$socialacc_beerdata_all);
                        log_message('debug', "{$activityName} Inserted socialMedia record into socialMediaAcct with value ".$this->webservicemodel->implode_with_key($insert_beersocialacc,'>',','));
                    }
                }
                else
                {
                	log_message('debug', "{$activityName} Beer had no Social account data to insert into socialMediaAcct table");
                }

                //insert ingredients
                if(isset($insert_ingredient)){
                  foreach($insert_ingredient AS $insert_beeringredient ){
                        $insert_beeringredient['beerId'] = $beerId;
                        $this->db->insert("beerIngredient",$insert_beeringredient);
                        log_message('debug', "{$activityName} Inserted ingredient record into beerIngredient with value ".$this->webservicemodel->implode_with_key($insert_beeringredient,'>',','));

                    }
                }
                else
                {
                	log_message('debug', "{$activityName} Beer had no ingredient data to insert into beerIngredient table");
                }

        }
      }
      $get_socialacc = $this->db->select()->where('id',$subAttributeId)->where('beerid',$beerId)->from('socialMediaAcct')->get()->row_array();
      if($get_socialacc){
          $social_id = $get_socialacc['id'];
          $this->db->where('id',$social_id);
          $this->db->update("socialMediaAcct",$socialacc_data);
          log_message('debug', "{$activityName} Updated record in socialMediaAcct with id={$social_id} with values ".$this->webservicemodel->implode_with_key($socialacc_data,'>',','));

      }

      log_message('debug', "{$activityName} completed processing");
    }

    //DeleteSocialMediaAcct function delete data from socialmediaacc table
    function DeleteSocialMediaAcct($beerdata,$subAttributeId){
    	$activityName = "DeleteSocialMediaAcct:";
        $masterbeerId = $beerdata['id'];
        $get_beerId = $this->db->select()->where('masterBeerId',$masterbeerId)->from('beer')->get()->row_array();
        if($get_beerId){
            $beerId = $get_beerId['id'];
            $this->db->where('id', $subAttributeId)->where('beerId',$beerId);
            $this->db->delete('socialMediaAcct');
            log_message('debug',"{$activityName} successfully deleted socialMediaAcct with {$subAttributeId} for beerID={$beerId}");
        }
    }

    function implode_with_key($assoc, $inglue = '>', $outglue = ',') {
		    $return = '';

		    foreach ($assoc as $tk => $tv) {
		        $return .= $outglue . $tk . $inglue . $tv;
		    }

		    return substr($return, strlen($outglue));
	}


    //InsertBeer function insert data into userbeer and beertable using userbeer table's id
    //userbeer table id is used as id of beer table
    function InsertUpdateBeer($beerdata,$timestamp,$action,$apiKey){
    	$activityName = "InsertUpdateBeer:";
        //userBeer table data
    	log_message('debug', "{$activityName} Processing action={$action}");

        $insert_userbeer['beerName'] = $beerdata['name'];
        $insert_userbeer['beerStyle'] = isset($beerdata['style']['name'])?$beerdata['style']['name']:'';
        $insert_userbeer['abv'] = isset($beerdata['abv'])?$beerdata['abv']:'';
        $insert_userbeer['ibu'] = isset($beerdata['ibu'])?$beerdata['ibu']:'';

        //beer profile data
        $insert_beerProfile['aroma'] = '0';
        $insert_beerProfile['sweet'] = '0';
        $insert_beerProfile['bitter'] = '0';
        $insert_beerProfile['malt'] = '0';
        $insert_beerProfile['yeast'] = '0';
        $insert_beerProfile['mouthFeel'] = '0';
        $insert_beerProfile['sour'] = '0';
        $insert_beerProfile['additive'] = '0';
        $insert_beerProfile['booziness'] = '0';
        $insert_beerProfile['createdon'] = $timestamp;

        //beer table data
        $insert_beer['name'] = $beerdata['name'];
        $insert_beer['abv'] = isset($beerdata['abv'])?$beerdata['abv']:'';
        $insert_beer['ibu'] = isset($beerdata['ibu'])?$beerdata['ibu']:'';
        $insert_beer['style'] = isset($beerdata['style']['name'])?$beerdata['style']['name']:'';
        $insert_beer['description'] = isset($beerdata['description'])?$beerdata['description']:'';
        $insert_beer['mediumLabel'] = isset($beerdata['labels']['medium'])?$beerdata['labels']['medium']:'';
        $insert_beer['largeLabel'] = isset($beerdata['labels']['large'])?$beerdata['labels']['large']:'';
        $insert_beer['icon'] = isset($beerdata['labels']['icon'])?$beerdata['labels']['icon']:'';

        //create beer brewery from response
        $breweries = isset($beerdata['breweries'])?$beerdata['breweries']:'';
        if($breweries){
            $i = 0;
            $brewery = '';
            foreach($breweries AS $brewery){
                $insert_brewey[$i]['name'] = $brewery['name'];
                $insert_brewey[$i]['masterBreweryId'] = $brewery['id'];
                $insert_brewey[$i]['description'] = isset($brewery['description'])?$brewery['description']:'';
                $insert_brewey[$i]['website'] = isset($brewery['website'])?$brewery['website']:'';
                $insert_brewey[$i]['established'] = isset($brewery['established'])?$brewery['established']:'';
                $insert_brewey[$i]['mediumImage'] = isset($brewery['images']['medium'])?$brewery['images']['medium']:'';
                $insert_brewey[$i]['largeImage'] = isset($brewery['images']['large'])?$brewery['images']['large']:'';
                $insert_brewey[$i]['icon'] = isset($brewery['images']['icon'])?$brewery['images']['icon']:'';
                $insert_brewey[$i]['mailingListUrl'] = isset($brewery['mailingListUrl'])?$brewery['mailingListUrl']:'';
                $insert_brewey[$i]['dateCreated'] =  $brewery['createDate'];
                $i++;
            }
        }

        //create social media account from response
        $socialAccounts = isset($beerdata['socialAccounts'])?$beerdata['socialAccounts']:'';
        if($socialAccounts){
            $i = 0;
            $socialAccount = '';
            foreach($socialAccounts AS $socialAccount){
                $insert_socialacc[$i]['id'] = $socialAccount['id'];
                $insert_socialacc[$i]['network'] = isset($socialAccount['socialMedia']['name'])?$socialAccount['socialMedia']['name']:'';
                $insert_socialacc[$i]['networkId'] = isset($socialAccount['socialMedia']['id'])?$socialAccount['socialMedia']['id']:'';
                $insert_socialacc[$i]['url'] = isset($socialAccount['link'])?$socialAccount['link']:'';
                $insert_socialacc[$i]['handle'] = isset($socialAccount['handle'])?$socialAccount['handle']:'';
                $insert_socialacc[$i]['handle'] = isset($socialAccount['handle'])?$socialAccount['handle']:'';
                $i++;
            }
        }

        //create beer ingredient
        $url_ingredient =  'http://api.brewerydb.com/v2/beer/'.$beerdata['id'].'/ingredients/?key='.$apiKey.'&format=php';
        $result_ingredient = $this->webservicemodel->get_web_page($url_ingredient);
        if($result_ingredient['message'] == 'Request Successful'){
            if(isset($result_ingredient['data'])){
              $ingredients = $result_ingredient['data'];
              if($ingredients){
                  $i = 0;
                  $ingredient = '';
                  foreach($ingredients AS $ingredient){
                      $insert_ingredient[$i]['id'] = $ingredient['id'];
                      $insert_ingredient[$i]['name'] = isset($ingredient['name'])?$ingredient['name']:'';
                      $insert_ingredient[$i]['category'] = isset($ingredient['category'])?$ingredient['category']:'';
                      $insert_ingredient[$i]['categoryDisplay'] = isset($ingredient['categoryDisplay'])?$ingredient['categoryDisplay']:'';
                      $insert_ingredient[$i]['dateCreated'] = $ingredient['createDate'];
                      $i++;
                  }
              }
            }

        }

        if($action == 'insert'){



            //insert into userbeer table
            $insert_userbeer['createdon'] = $timestamp;
            $insert_userbeer['brewery'] =  isset($beerdata['breweries']['0']['name'])?$beerdata['breweries']['0']['name']:'';
            $this->db->insert("userBeer",$insert_userbeer);
            $beerId = $this->db->insert_id();

            log_message('debug',"{$activityName} Inserted record in UserBeer with id={$beerId}, name=".$insert_userbeer['beerName'].",brewery=".$insert_userbeer['brewery']);
            $insert_beerProfile['beerId'] = $beerId;
            $this->db->insert("beerProfile",$insert_beerProfile);
            log_message('debug', "{$activityName} Inserted record into BeerProfile with value ". $this->webservicemodel->implode_with_key($insert_beerProfile, '>',','));

            $insert_beer['id'] = $beerId;
            $insert_beer['datecreated '] = $beerdata['createDate'];
            $insert_beer['masterBeerId'] = $beerdata['id'];
            //insert intp beer table
            $this->db->insert("beer",$insert_beer);

            log_message('debug', "{$activityName} Inserted record into Beer with value ".$this->webservicemodel->implode_with_key($insert_beer,'>', ','));

            if(isset($insert_brewey)){
              foreach($insert_brewey AS $insert_beerbrewery ){
                  $insert_beerbrewery['beerId'] = $beerId;
                  $this->db->insert("beerBrewery",$insert_beerbrewery);

                  log_message('debug', "{$activityName} Inserted brewery record into BeerBrewery with value ".$this->webservicemodel->implode_with_key($insert_beerbrewery,'>',','));
              }
            }
            else
            {
            	log_message('debug', "{$activityName} Beer had no Brewery data to insert into BeerBrewery table");
            }
            //insert social accounts
            if(isset($insert_socialacc)){
              foreach($insert_socialacc AS $insert_beersocialacc ){
                    $insert_beersocialacc['beerId'] = $beerId;
                    $this->db->insert("socialMediaAcct",$insert_beersocialacc);
                    log_message('debug', "{$activityName} Inserted socialMedia record into socialMediaAcct with value ".$this->webservicemodel->implode_with_key($insert_beersocialacc,'>',','));
                }
            }
            else
            {
            	log_message('debug', "{$activityName} Beer had no Social account data to insert into socialMediaAcct table");
            }
            //insert ingredients
            if(isset($insert_ingredient)){
              foreach($insert_ingredient AS $insert_beeringredient ){
                    $insert_beeringredient['beerId'] = $beerId;
                    $this->db->insert("beerIngredient",$insert_beeringredient);
                    log_message('debug', "{$activityName} Inserted ingredient record into beerIngredient with value ".$this->webservicemodel->implode_with_key($insert_beeringredient,'>',','));
                }
            }
            else
            {
            	log_message('debug', "{$activityName} Beer had no ingredient data to insert into beerIngredient table");

            }

        }
        if($action == 'edit'){
             $masterbeerId = $beerdata['id'];
             $get_beerId = $this->db->select()->where('masterBeerId',$masterbeerId)->from('beer')->get()->row_array();
             if($get_beerId){
                $beerId = $get_beerId['id'];
                log_message('debug', "{$activityName} Found beerID={$beerId} for masterBeerId={$masterbeerId}");

                $this->db->where('id',$beerId);
  		        $this->db->update("userBeer",$insert_userbeer);

  		        log_message('debug', "{$activityName} Updated record in userBeer for id={$beerId} with values ".$this->webservicemodel->implode_with_key($insert_userbeer,'>',','));

                $this->db->where('id',$beerId);
  		        $this->db->update("beer",$insert_beer);

  		        log_message('debug', "{$activityName} Updated record in Beer for id={$beerId} with values ".$this->webservicemodel->implode_with_key($insert_beer, '>','.'));

             }else{

             	log_message('debug', "{$activityName} Did not find beerID in Beer table for masterBeerId={$masterbeerId}");

                $beerName  = trim($beerdata['name']);
                $breweryName = isset($beerdata['breweries']['0']['name'])?$beerdata['breweries']['0']['name']:'';
                $brewery = trim($breweryName);
                $validateBeer = $this->db->select()->where('beerName',$beerName)->where('brewery',$brewery)->from('userBeer')->get()->row_array();
                if($validateBeer){
                    $beerId = $validateBeer['id'];
                    log_message('debug', "{$activityName} found beerId={$beerId} in UserBeer table with name={$beerName} and brewery={$brewery}");

                    $this->db->where('id',$beerId);
                    $this->db->update("userBeer",$insert_userbeer);

                    log_message('debug', "{$activityName} Updated UserBeer record with values ".$this->webservicemodel->implode_with_key($insert_userbeer,'>',','));

                    //insert new beer
                    $insert_beer['id'] = $beerId;
                    $insert_beer['datecreated '] = $beerdata['createDate'];
                    $insert_beer['masterBeerId'] = $beerdata['id'];
                    //insert intp beer table
                    $this->db->insert("beer",$insert_beer);
                    log_message('debug', "{$activityName} Inserted Beer record with values ".$this->webservicemodel->implode_with_key($insert_beer,'>',','));

                    //insert new brewery
                    if(isset($insert_brewey)){
                      foreach($insert_brewey AS $insert_beerbrewery ){
                          $insert_beerbrewery['beerId'] = $beerId;
                          $this->db->insert("beerBrewery",$insert_beerbrewery);
                          log_message('debug', "{$activityName} Inserted brewery record into BeerBrewery with value ".$this->webservicemodel->implode_with_key($insert_beerbrewery,'>',','));
                      }
                    }

                    //insert new socialMediaAccount
                    if(isset($insert_socialacc)){
                      foreach($insert_socialacc AS $insert_beersocialacc ){
                          $insert_beersocialacc['beerId'] = $beerId;
                          $this->db->insert("socialMediaAcct",$insert_beersocialacc);
                          log_message('debug', "{$activityName} Inserted socialMedia record into socialMediaAcct with value ".$this->webservicemodel->implode_with_key($insert_beersocialacc,'>',','));
                      }
                    }

                    if(isset($insert_ingredient)){
                      foreach($insert_ingredient AS $insert_beeringredient ){
                            $insert_beeringredient['beerId'] = $beerId;
                            $this->db->insert("beerIngredient",$insert_beeringredient);

                            log_message('debug', "{$activityName} Inserted ingredient record into beerIngredient with value ".$this->webservicemodel->implode_with_key($insert_beeringredient,'>',','));

                        }
                    }

                }
             }
             if(isset($beerId)){
               $get_beerProfileId = $this->db->select()->where('beerId',$beerId)->from('beerProfile')->get()->row_array();
                if(!$get_beerProfileId){
                    $insert_beerProfile['beerId'] = $beerId;
                    $this->db->insert("beerProfile",$insert_beerProfile);

                    log_message('debug', "{$activityName} Inserted BeerProfile record with value ".$this->webservicemodel->implode_with_key($insert_beerProfile,'>',','));
                }
             }
        }
        log_message('debug', "{$activityName} completed execution");
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