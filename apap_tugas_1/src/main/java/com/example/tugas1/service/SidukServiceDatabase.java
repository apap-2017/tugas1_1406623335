package com.example.tugas1.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.SidukMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SidukServiceDatabase implements SidukService{

	@Autowired
	private SidukMapper sidukMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		return sidukMapper.selectPenduduk(nik);
	}

	@Override
	public KeluargaModel selectKeluarga(BigInteger id_keluarga, String nkk) {
		return sidukMapper.selectKeluarga(id_keluarga, nkk);
	}

	@Override
	public KelurahanModel selectKelurahan(BigInteger id_kelurahan, String nama_kelurahan) {
		return sidukMapper.selectKelurahan(id_kelurahan,nama_kelurahan);
	}

	@Override
	public KecamatanModel selectKecamatan(BigInteger id_kecamatan, String nama_kecamatan) {
		return sidukMapper.selectKecamatan(id_kecamatan, nama_kecamatan);
	}

	@Override
	public KotaModel selectKota(BigInteger id_kota, String nama_kota) {
		return sidukMapper.selectKota(id_kota, nama_kota);
	}

	@Override
	public KeluargaModel lihatListKeluarga(String nkk, BigInteger id_keluarga) {
		// TODO Auto-generated method stub
		return sidukMapper.lihatListKeluarga(nkk, id_keluarga);
	}

	@Override
	public String hitungJmlPenduduk() {
		// TODO Auto-generated method stub
		return sidukMapper.hitungJmlPenduduk();
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		sidukMapper.addPenduduk(penduduk);		
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		sidukMapper.addKeluarga(keluarga);
	}

	@Override
	public String getNikPenduduk(String digitNIK2, int jenis_kelamin) {
		// TODO Auto-generated method stub
		String nik = sidukMapper.getNikPenduduk(digitNIK2,jenis_kelamin);
		String nikEnc = "";
		
		if(nik == null) {
			nikEnc = digitNIK2 + String.format("%04d", 1);
		} else {
			int nikSubstr = Integer.parseInt(nik.substring(nik.length()-4, nik.length())) + 1; // max 9998
			nikEnc = digitNIK2 + String.format("%04d",nikSubstr);
		}
		return nikEnc;
	}

	@Override
	public String hitungJmlKeluarga() {
		// TODO Auto-generated method stub
		return sidukMapper.hitungJmlKeluarga();
	}

	@Override
	public String getNkkKeluarga(String digitNKK2) {
		// TODO Auto-generated method stub
		String nkk = sidukMapper.getNkkKeluarga(digitNKK2);
		String nkkEnc = "";
		
		if(nkk == null) {
			nkkEnc = digitNKK2 + String.format("%04d", 1);
		} else {
			int nkkSubstr = Integer.parseInt(nkk.substring(nkk.length()-4, nkk.length())) + 1; // max 9998
			nkkEnc = digitNKK2 + String.format("%04d",nkkSubstr);			
		}
		return nkkEnc;
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		sidukMapper.updatePenduduk(penduduk);
	}

	@Override
	public void updateNikPenduduk_Keluarga(String digitNKK1, BigInteger id_keluarga) {
		// TODO Auto-generated method stub
		sidukMapper.updateNikPenduduk_Keluarga(digitNKK1, id_keluarga);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		sidukMapper.updateKeluarga(keluarga);
	}

	@Override
	public void ubahBerlakuKeluarga(BigInteger id) {
		// TODO Auto-generated method stub
		sidukMapper.ubahBerlakuKeluarga(id);
	}

	@Override
	public void updatePendudukMati(int is_wafat, BigInteger id) {
		// TODO Auto-generated method stub
		sidukMapper.updatePendudukMati(is_wafat, id);
	}

}
