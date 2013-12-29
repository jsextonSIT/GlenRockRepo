<?PHP
	include 'config.php';

	$con = mysqli_connect(HOST, USERNAME, PASSWORD, DB_NAME);

	// Check connection
	if (mysqli_connect_errno($con))
	{
	  echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	// function for querying
	function getCategory($con, $date)
	{
		$use = "USE database glenrocknj";
		//$select1 = "(SELECT Event_Title, Event_Location, Event_Address, Event_Start, Event_StartTime, Event_Contact, Event_Email, Event_Phone, Event_Website";
		//$from1 = " FROM events";

		//$where1 = " WHERE Event_Title = 'Rear Yard Garbage Pickup - North/West' ORDER BY Event_Start)";
		//$union = " UNION ";
		//$where2 = " WHERE Event_Title = 'Rear Yard Garbage Pickup - South/East' ORDER BY Event_Start)";
		//$where3 = " WHERE Event_Title = 'Curbside Cardboard Pickup - South/East' ORDER BY Event_Start)";
		//$where4 = " WHERE Event_Title = 'Curbside Cardboard Pickup - North/West' ORDER BY Event_Start)";


		//$query = $select1 . $from1 . $where1 . $union . $select1 . $from1 . $where2 . $union . $select1 . $from1 . $where3 . $union . $select1 . $from1 . $where4;


		$select0 = " SELECT Event_Id, Event_Title, Event_Description, Event_Location, Event_Address, Event_Start, Event_StartTime, Event_Contact, Event_Email, Event_Phone, Event_Website";
		$from0 = " FROM events";
		$where0 = " WHERE Event_Start >= '" . $date . "' AND (Event_Title = 'Rear Yard Garbage Pickup - North/West' OR Event_Title = 'Rear Yard Garbage Pickup - South/East' OR Event_Title = 'Curbside Cardboard Pickup - South/East' OR Event_Title = 'Curbside Cardboard Pickup - North/West') ";
		$orderby0 = " ORDER BY Event_Start ASC LIMIT 7";

		$newquery = $select0 . $from0 . $where0 . $orderby0;
		$result = mysqli_query($con, $use);
		$result = mysqli_query($con, $newquery);

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

	//$date = "2012-04-03";

	// response from the server
	$server_response = array();

	$date = $_POST['date'];
	$category = getCategory($con, $date);

	// echo it out
	if(is_array($category))
	{
		$server_response["trash"] = $category;
		echo json_encode($server_response);
	}

	mysqli_close($con);
?>
