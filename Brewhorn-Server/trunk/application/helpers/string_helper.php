<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
 
if (!function_exists('implode_with_key'))
{
    function implode_with_key($assoc, $inglue = '>', $outglue = ',') {
		    $return = '';
		 
		    foreach ($assoc as $tk => $tv) {
		        $return .= $outglue . $tk . $inglue . $tv;
		    }
		 
		    return substr($return, strlen($outglue));
	}

}