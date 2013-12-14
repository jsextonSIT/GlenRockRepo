<?PHP
	include 'config.php';

	$con = mysqli_connect(HOST, USERNAME, PASSWORD, DB_NAME);

	// Check connection
	if (mysqli_connect_errno($con))
	{
	  echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	// function for querying
	function getCategory($con)
	{
		$use = "USE database glenrocknj";
		$select = "SELECT Event_Title, Event_Description, Event_Start, Event_End, Event_StartTime, Event_Location, Event_Address, Event_Contact, Event_Email, Event_Phone, Event_Website";
		$from = " FROM events";
		$where = " ORDER BY Event_Start DESC"; 

		$query = $select . $from . $where;

		$result = mysqli_query($con, $use);	
		$result = mysqli_query($con, $query);

		if (!$result) 
		{
			die('Invalid query: ' . mysql_error());
		}

		while($row=mysqli_fetch_array($result, MYSQLI_ASSOC)) 
		{
			$cat[] = $row;
		}

		return $cat;
	}

	// response from the server
	$server_response = array();
	$category = getCategory($con);

	// echo it out 
	if(is_array($category)) 
	{
		$server_response["calendar"] = $category;
		echo json_encode($server_response);
	}

	mysqli_close($con);
?>
