<!-- buat check optimasi query-->
<html>
	<body>
		<?php
			$servername = "localhost";
			$username = "root";
			$password = "";
			$dbname = "tugas1apap";

			// Create connection
			$conn = new mysqli($servername, $username, $password, $dbname);
			
			$np = $_GET['id_keluarga'];

			$query = "SELECT * FROM keluarga where id='$np'";
			$result = mysqli_query($conn, $query);
			$row = mysqli_fetch_assoc($result);

			if (mysqli_num_rows($result) > 0) {
				echo "id keluarga: ". $np."<br>Nomor KK: ".$row['nomor_kk'];
			} else {
				echo "Peserta ". $np." tidak terdaftar";
			}
			mysqli_close($conn);
		?>
		
	</body>
</html>