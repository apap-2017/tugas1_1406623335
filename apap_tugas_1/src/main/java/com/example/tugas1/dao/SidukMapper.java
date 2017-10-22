package com.example.tugas1.dao;

import java.math.BigInteger;
import java.util.List;

//import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;

/* untuk pemanggilan query database panggil column yang dibutuhkan saja */

@Mapper
public interface SidukMapper {

	/* Soal 1*/
	@Select("select * from penduduk where nik=#{nik}")
	PendudukModel selectPenduduk(@Param("nik") String nik);

	@Select("select * from keluarga where id=#{id_keluarga} or nomor_kk=#{nkk}")
	KeluargaModel selectKeluarga(@Param("id_keluarga") BigInteger id_keluarga, @Param("nkk") String nkk);

	@Select("select * from kelurahan where id=#{id_kelurahan} or nama_kelurahan=#{nama_kelurahan}")
	KelurahanModel selectKelurahan(@Param("id_kelurahan") BigInteger id_kelurahan, @Param("nama_kelurahan") String nama_kelurahan);

	@Select("select * from kecamatan where id=#{id_kecamatan} or nama_kecamatan=#{nama_kecamatan}")
	KecamatanModel selectKecamatan(@Param("id_kecamatan") BigInteger id_kecamatan, @Param("nama_kecamatan") String nama_kecamatan);

	@Select("select * from kota where id=#{id_kota} or nama_kota=#{nama_kota}")
	KotaModel selectKota(@Param("id_kota") BigInteger id_kota, @Param("nama_kota") String nama_kota);
	///////////

	/* Soal 2*/
	@Select("select * from keluarga where nomor_kk=#{nkk} or id=#{id_keluarga}")
	@Results(value = {
			@Result(property="id",column="id"),
			@Result(property="nomor_kk",column="nomor_kk"),
			@Result(property="alamat",column="alamat"),
			@Result(property="RT",column="RT"),
			@Result(property="RW",column="RW"),
			@Result(property="id_kelurahan",column="id_kelurahan"),
			@Result(property="is_tidak_berlaku",column="is_tidak_berlaku"),
			@Result(property="penduduk",column="id",javaType=List.class,many=@Many(select="tampilListKeluarga"))
	})
	KeluargaModel lihatListKeluarga(@Param("nkk") String nkk, @Param("id_keluarga") BigInteger id_keluarga);

	@Select("select * from penduduk where id_keluarga=#{id}")
	List<PendudukModel> tampilListKeluarga (@Param("id") String id);
	///////////
	
	/* Soal 3*/	
	@Select("select max(id) from penduduk")
	String hitungJmlPenduduk();

	@Select("select max(nik) from penduduk where substr(nik,1,12)=#{digitNIK2} and jenis_kelamin=#{jenis_kelamin}")
	String getNikPenduduk(@Param("digitNIK2") String digitNIK2, @Param("jenis_kelamin") int jenis_kelamin);
	
	@Insert("insert into penduduk (id,nik,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,is_wni,id_keluarga,agama,pekerjaan,status_perkawinan,status_dalam_keluarga,golongan_darah,is_wafat) values (#{id},#{nik},#{nama},#{tempat_lahir},#{tanggal_lahir},#{jenis_kelamin},#{is_wni},#{id_keluarga},#{agama},#{pekerjaan},#{status_perkawinan},#{status_dalam_keluarga},#{golongan_darah},#{is_wafat})")
	void addPenduduk (PendudukModel penduduk);
	///////////
	
	/* Soal 4*/	
	@Select("Select max(id) from keluarga")
	String hitungJmlKeluarga();

	@Select("select max(nomor_kk) from keluarga where substr(nomor_kk,1,12)=#{digitNKK2}")
	String getNkkKeluarga(@Param("digitNKK2") String digitNKK2);
	
	@Insert("insert into keluarga (id,nomor_kk,alamat,RT,RW,id_kelurahan,is_tidak_berlaku) values (#{id},#{nomor_kk},#{alamat},#{RT},#{RW},#{id_kelurahan},#{is_tidak_berlaku})")
	void addKeluarga(KeluargaModel keluarga);
	///////////

	/* Soal 5*/
	@Update("update penduduk set nik=#{nik}, nama=#{nama}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin}, is_wni=#{is_wni}, id_keluarga=#{id_keluarga}, agama=#{agama}, pekerjaan=#{pekerjaan}, status_perkawinan=#{status_perkawinan}, status_dalam_keluarga=#{status_dalam_keluarga}, golongan_darah=#{golongan_darah}, is_wafat=#{is_wafat} where id=#{id}")
	void updatePenduduk(PendudukModel penduduk);
	///////////

	/* Soal 6*/
	@Update("update keluarga set nomor_kk=#{nomor_kk}, alamat=#{alamat}, RT=#{RT}, RW=#{RW}, id_kelurahan=#{id_kelurahan} where id=#{id}")
	void updateKeluarga(KeluargaModel keluarga);
	
	@Update("update penduduk set nik=concat(#{digitNKK1},substr(nik,6,LENGTH(nik))) where id_keluarga=#{id_keluarga}")
	void updateNikPenduduk_Keluarga(@Param("digitNKK1") String digitNKK1, @Param("id_keluarga") BigInteger id_keluarga);
	///////////


	/* Soal 7*/	
	@Update("update keluarga set is_tidak_berlaku=1 where id=#{id}")
	void ubahBerlakuKeluarga(@Param("id") BigInteger id);

	@Update("update penduduk set is_wafat=#{is_wafat} where id=#{id}")
	void updatePendudukMati(@Param("is_wafat") int is_wafat, @Param("id") BigInteger id);
	///////////

	/* Soal 8*/
	@Select("select * from kota)
	List<KotaModel> selectKotaModel();
	///////////
}
