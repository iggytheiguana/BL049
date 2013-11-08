<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');  

set_time_limit (0);
/**
 * 
 * @author Kapil
 */
class wsController extends CI_Controller {
	
	public function __construct()
	{
	    parent::__construct();
		$this->load->database();
		$this->load->model('wsmodel');
	}
	
	function recommendBeer($userId = NULL){
		
		$userId = $_GET['userId'];
		$response=$this->wsmodel->recommendBeer($userId);//Model Function call 
		echo $response;								// return response to mobile.
		die;
	}
	
	/****
	 ***	Function to fetch data from the brewerydb. In this we are fetching beer Name, IBU , ABV, Style, brewery Name. If beer already exists in our database then we are updating the record in our database without updating their taste profile. If beer doesn't exist in the database then we are inserting the beer with the taste parameters with zero values into our database. In This We are fetching the number of pages . After fetching total number of pages we are fetching all the records of all pages.
	 ***
	 ****/
	function apiTest($abv=NULL,$page=NULL){
		$date = strtotime(date("Y-m-d H:i:s"));
		$page = $_GET['page'];
		$abv = $_GET['abv'];
		$apiKey = '75ca1c53de40741205e76c8c3a7464c7';
		
		$url = 'http://api.brewerydb.com/v2/beers?abv='.$abv.'&key='.$apiKey.'&p='.$page.'&format=PHP&withBreweries=Y';
		$result = $this -> get_web_page($url);
		
		//echo "<pre>";print_R($result); die;
		if (!empty($result)):
			$currentPage = $result->currentPage;
			$numberOfPages = $result->numberOfPages;
			$totalResults = $result->totalResults;
			$data = $result->data;
			//echo $numberOfPages;die;
			
			if($numberOfPages > 1){
				for($i=1; $i<=$numberOfPages;$i++){
					$pageNumber = $i;
					$url = 'http://api.brewerydb.com/v2/beers?abv='.$abv.'&key='.$apiKey.'&p='.$pageNumber.'&format=PHP&withBreweries=Y';
					
					$result1 = $this -> get_web_page($url);
					
					//echo "<pre>";print_R($result); die;
					if (!empty($result1)){
						if(isset($result1->data)){
							$data1 = $result1->data;
							if(!empty($data1)):
								$count1 =  count($data1);
								foreach($data1 as $row1):
									if(isset($row1->name)):
										$name1 = trim($row1->name);
									else:
										$name1 = '';
									endif;
									if(isset($row1->abv)):
										$abv1 = trim($row1->abv);
									else:
										$abv1 = '';
									endif;
									if(isset($row1->ibu)):
										$ibu1 = trim($row1->ibu);
									else:
										$ibu1 = '';
									endif;
									if(isset($row1->style->name)):
										$style1 = trim($row1->style->name);
									else:
										$style1 = '';
									endif;
									if(isset($row1->breweries)):
										$breweries1 = $row1->breweries;
										if($breweries1):
											$breweryName1 = trim($breweries1[0]->name);
										else:
											$breweryName1 = '';
										endif;
									else:
										$breweryName1 = '';
									endif;
									$validateBeer = $this->db->select('id')->where(array("brewery"=>$breweryName1,"beerName"=>$name1))->from('userBeer')->get()->row_array();
									if($validateBeer):
										$beerId = $validateBeer['id'];
										$this->db->where('id',$beerId);
										$this->db->update('userBeer',array("brewery"=>$breweryName1,"beerName"=>$name1,"beerStyle"=>$style1,"abv"=>$abv1,"ibu"=>$ibu1,"createdon"=>$date));
										
										
										$validateProfile = $this->db->select('id')->where('beerId',$beerId)->from('beerProfile')->get()->row_array();
										if($validateProfile):
											
										else:
											$beerProfile = array(
													"beerId"=>$beerId,
													"aroma"=>0,
													"sweet"=>0,
													"bitter"=>0,
													"malt"=>0,
													"yeast"=>0,
													"mouthFeel"=>0,
													"sour"=>0,
													"additive"=>0,
													"booziness"=>0,
													"createdon"=>$date,
													);
											$this->db->insert("beerProfile",$beerProfile);
										endif;
									else:
									
									
										$this->db->insert('userBeer',array("brewery"=>$breweryName1,"beerName"=>$name1,"beerStyle"=>$style1,"abv"=>$abv1,"ibu"=>$ibu1,"createdon"=>$date));
										$beerId = $this->db->insert_id();
										
										$beerProfile = array(
												"beerId"=>$beerId,
												"aroma"=>0,
												"sweet"=>0,
												"bitter"=>0,
												"malt"=>0,
												"yeast"=>0,
												"mouthFeel"=>0,
												"sour"=>0,
												"additive"=>0,
												"booziness"=>0,
												"createdon"=>$date,
												);
										$this->db->insert("beerProfile",$beerProfile);
									
									
									endif;
								endforeach;
								
							else:
								
							endif;
							
						}	
						
					}
				}
				echo "Data import successfully.";
			}else{
				
				if(!empty($data)):
					$count =  count($data);
					foreach($data as $row):
						if(isset($row->name)):
							$name = trim($row->name);
						else:
							$name = '';
						endif;
						if(isset($row->abv)):
							$abv = trim($row->abv);
						else:
							$abv = '';
						endif;if(isset($row->ibu)):
							$ibu = trim($row->ibu);
						else:
							$ibu = '';
						endif;if(isset($row->style->name)):
							$style = trim($row->style->name);
						else:
							$style = '';
						endif;
						if(isset($row->breweries)):
							$breweries = $row->breweries;
							if($breweries):
								$breweryName = trim($breweries[0]->name);
							else:
								$breweryName = '';
							endif;
						else:
							$breweryName = '';
						endif;
						$validateBeer = $this->db->select()->where(array("brewery"=>$breweryName,"beerName"=>$name))->from('userBeer')->get()->row_array();
						if($validateBeer):
							
							$beerId = $validateBeer['id'];
							$this->db->where('id',$beerId);
							
							$this->db->update('userBeer',array("brewery"=>$breweryName1,"beerName"=>$name1,"beerStyle"=>$style1,"abv"=>$abv1,"ibu"=>$ibu1,"createdon"=>$date));
							
							
							$validateProfile = $this->db->select('id')->where('beerId',$beerId)->from('beerProfile')->get()->row_array();
							if($validateProfile):
								
							else:
								
								
								$beerProfile = array(
										"beerId"=>$beerId,
										"aroma"=>0,
										"sweet"=>0,
										"bitter"=>0,
										"malt"=>0,
										"yeast"=>0,
										"mouthFeel"=>0,
										"sour"=>0,
										"additive"=>0,
										"booziness"=>0,
										"createdon"=>$date,
										);
								$this->db->insert("beerProfile",$beerProfile);
							endif;
						else:
							$this->db->insert('userBeer',array("brewery"=>$breweryName,"beerName"=>$name,"beerStyle"=>$style,"abv"=>$abv,"ibu"=>$ibu,"createdon"=>$date));
							$beerId = $this->db->insert_id();
							
							
							$beerProfile = array(
									"beerId"=>$beerId,
									"aroma"=>0,
									"sweet"=>0,
									"bitter"=>0,
									"malt"=>0,
									"yeast"=>0,
									"mouthFeel"=>0,
									"sour"=>0,
									"additive"=>0,
									"booziness"=>0,
									"createdon"=>$date,
									);
							$this->db->insert("beerProfile",$beerProfile);
							
							
						 endif;
					endforeach;
				
					echo "Data import successfully.";
				else:
					echo "No data";
				endif;
			}
			
		else:
			echo "No Record Found";
		endif;
		
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
		
        $array = json_decode($content);
		//echo "<pre>";print_R($array);die;
        return $array;
    }
	
	
}