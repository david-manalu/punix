<?php
function captureOrder($orderID) {
    // Get the accessToken from MainActivity
    $accessToken = "A21AAJPDe-Ath7bQw6PUhRaKn3Ur3AK8qmcs5JBES_hWIoBuq7fsKHTJ4oV9kjiYETHhjcCbaikgyhT2aEdUeiJLRe2ONEy9A";

    $headers = [
        'Accept: application/json',
        'Content-type: application/json',
        'Authorization: Bearer ' . $accessToken
    ];

    $url = 'https://api-m.sandbox.paypal.com/v2/checkout/orders/' . $orderID . '/capture';

    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_POST, true);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);

    $response = curl_exec($curl);
    $statusCode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
    curl_close($curl);


    if ($statusCode >= 200 && $statusCode < 300) {
        // Handle success response
        $jobj = json_decode($response, true);
        return $jobj;
    } else {
        die("Failed fetching response");
    }
}

if (isset($_GET['token'])) {

    $res = captureOrder($_GET['token']);

    //specify the server name and here it is localhost
    $server_name = "localhost";

    //specify the username - here it is root
    $user_name = "root";

    //specify the password - it is empty
    $password = "";

    // Creating the connection by specifying the connection details
    $connection = mysqli_connect($server_name, $user_name, $password);

    // Checking the  connection
    if (!$connection) {
        die("Failed ". mysqli_connect_error());
    }

    mysqli_select_db($connection, "punix");

    $query = "UPDATE `transactions` SET `status` = '" . $res["status"] . "' WHERE `transactions`.`token` = '" . $res["id"] . "'";

    if (mysqli_query($connection, $query)) {
    } else {
    }
    $query = "SELECT `total` FROM `transactions` WHERE `token` = '" . $res["id"] . "'";
    $result = mysqli_query($connection, $query);
    $row = mysqli_fetch_assoc($result);
    $total = $row['total'];

    
}
?>
<!DOCTYPE html>
<html>
<head>
  <title>Order Confirmation</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap');

    h1 {
      color: white;
      font-family: 'Montserrat', sans-serif;
      font-size: 96px;
      font-weight: 700;
      text-align:center;
    }

    p {
      color: white;
      font-family: 'Montserrat', sans-serif;
      font-size: 48px;
      margin: 0;
    }

    body {
      background-color: green;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .checkmark-container {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        width: 80%;
    }

    .checkmark {
      width: 300px;
      height: 300px;
      background-color: white;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 0;
    }

    .checkmark img {
      width: 200px;
      height: 200px;
    }
  </style>
</head>
<body>
  <div class="checkmark-container">
    <h1 style="color: white;">ORDER COMPLETE</h1>
    <br>
    <br>
    <div class="checkmark">
      <h1 style="color:green; font-size:200px;">&#10003;</h1>
    </div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <!-- <p>#5CN3813469579390C</p>
    <p>Rp. 270000</p> -->
    <p>Order #<?php echo $res["id"] ?></p>
    <p>Rp. <?php echo $total ?></p>
  </div>
</body>
</html>
