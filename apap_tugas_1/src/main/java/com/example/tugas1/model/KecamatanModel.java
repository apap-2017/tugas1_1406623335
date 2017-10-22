package com.example.tugas1.model;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private BigInteger id;
	public BigInteger id_kota;
	private String kode_kecamatan;
	private String nama_kecamatan;
}
