package com.example.tugas1.service;

import java.math.BigInteger;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

public interface SidukService {
	
	PendudukModel selectPenduduk (String nik);

	KeluargaModel selectKeluarga(BigInteger id_keluarga, String nkk);

	KelurahanModel selectKelurahan(BigInteger id_kelurahan, String nama_kelurahan);

	KecamatanModel selectKecamatan(BigInteger id_kecamatan, String nama_kecamatan);

	KotaModel selectKota(BigInteger id_kota, String nama_kota);

	KeluargaModel lihatListKeluarga(String nkk, BigInteger id_keluarga);

	String hitungJmlPenduduk();

	void addPenduduk(PendudukModel penduduk);

	void addKeluarga(KeluargaModel keluarga);

	String getNikPenduduk(String digitNIK2, int jenis_kelamin);

	String hitungJmlKeluarga();

	String getNkkKeluarga(String digitNKK2);

	void updatePenduduk(PendudukModel penduduk);

	void updateKeluarga(KeluargaModel keluarga);

	void updateNikPenduduk_Keluarga(String digitNKK1, BigInteger id_keluarga);

	void ubahBerlakuKeluarga(BigInteger id);

	void updatePendudukMati(int is_wafat, BigInteger id);
	
	List<KotaModel> selectListKota();
}
