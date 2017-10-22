package com.example.tugas1.model;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private BigInteger id;
	private String nomor_kk;
	private String alamat;
	private String RT;
	private String RW;
	public BigInteger id_kelurahan;
	private boolean is_tidak_berlaku;
	private List<PendudukModel> penduduk;
}
