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
class wsmodel extends CI_Model {


    public function __construct() {
        parent::__construct();
        $this -> load -> database();
    }
	
	function recommendBeer($userId){
		$userTaste = $this->db->select()->where('userId',$userId)->from('CraftBeer.userTaste')->get()->row_array();
		if($userTaste):
			echo "<pre>";print_R($userTaste);die;
			return json_encode();
		else:
			return json_encode(array("recommendBeer"=>array()));
		endif;
	
	}
}