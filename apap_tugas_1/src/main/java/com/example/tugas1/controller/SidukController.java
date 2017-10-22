package com.example.tugas1.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.SidukService;

@Controller
public class SidukController {
	
	@Autowired
	SidukService sidukDAO;
	
	/*Soal 1*/
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String viewPenduduk(@RequestParam(value="nik") String nik, Model model) {
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		System.out.println("test"+penduduk.getId_keluarga());
		KeluargaModel keluarga = sidukDAO.selectKeluarga(penduduk.getId_keluarga(),null);
		System.out.println(keluarga);
		KelurahanModel kelurahan = sidukDAO.selectKelurahan(keluarga.getId_kelurahan(),null);
		KecamatanModel kecamatan = sidukDAO.selectKecamatan(kelurahan.getId_kecamatan(),null);
		KotaModel kota = sidukDAO.selectKota(kecamatan.getId_kota(),null);
		
		if(penduduk != null && keluarga != null && kelurahan != null && kecamatan != null && kota != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "lihatDataPenduduk";
		}
		
		return "not-found";
	}
	//////////
	
	/*Soal 2*/
	@RequestMapping("/keluarga")
	public String viewKeluarga(@RequestParam(value="nkk") String nkk, Model model) {
		KeluargaModel keluarga = sidukDAO.lihatListKeluarga(nkk, null);
		KelurahanModel kelurahan = sidukDAO.selectKelurahan(keluarga.getId_kelurahan(),null);
		KecamatanModel kecamatan = sidukDAO.selectKecamatan(kelurahan.getId_kecamatan(),null);
		KotaModel kota = sidukDAO.selectKota(kecamatan.getId_kota(),null);
		
		if(keluarga != null && kelurahan != null && kecamatan != null && kota != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "lihatDataKeluarga";
		}
		return "not-found";
	}
	//////////
	
	/*Soal 3*/
	@RequestMapping("/penduduk/tambah")
	public String tambahPenduduk(Model model) {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk",penduduk);
		return "form-tambah-penduduk";
	}
	
	@PostMapping("/penduduk/tambah")
	public String viewTambahPenduduk(@ModelAttribute PendudukModel penduduk, Model model) {
		System.out.println("jumlah penduduk " + penduduk);
		/*segera buat cheking untuk NIK*/
		KeluargaModel keluarga = sidukDAO.selectKeluarga(penduduk.getId_keluarga(), null);
		if(keluarga == null) {
			return "not-found";
		}
		KelurahanModel kelurahan = sidukDAO.selectKelurahan(keluarga.getId_kelurahan(),null);
		if(kelurahan == null) {
			return "not-found";
		}
		KecamatanModel kecamatan = sidukDAO.selectKecamatan(kelurahan.getId_kecamatan(),null);
		if(kecamatan == null) {
			return "not-found";
		}
		// KotaModel kota = sidukDAO.selectKota(kecamatan.id_kota,null);
		
		// untuk mendapatkan nik
		String idKecamatan = kecamatan.getKode_kecamatan();
		String digitNIK1 = idKecamatan.substring(0, idKecamatan.length()-1);
		
		String[] datePenduduk = penduduk.getTanggal_lahir().split("-");
		String digitNIK2 = digitNIK1 + datePenduduk[2] + datePenduduk[1] + datePenduduk[0].substring(datePenduduk[0].length()-2, datePenduduk[0].length());
		if(penduduk.getJenis_kelamin()==1) {
			digitNIK2 = digitNIK2.substring(0, 6) + (Integer.parseInt(digitNIK2.substring(6,7))+4) + digitNIK2.substring(7,digitNIK2.length());
		}
		
		String digitNIK3 = sidukDAO.getNikPenduduk(digitNIK2,penduduk.getJenis_kelamin());
		penduduk.setNik(digitNIK3);
		
		// counting max id penduduk
		BigInteger jmlPenduduk = new BigInteger(sidukDAO.hitungJmlPenduduk());
		BigInteger jmlPendudukCounter = new BigInteger("1");
		jmlPenduduk = jmlPenduduk.add(jmlPendudukCounter);
		penduduk.setId(jmlPenduduk);
		
		// untuk testing status dalam penduduk
		System.out.println("Penduduk " + penduduk);
		
		// tambah penduduk
		try {
			sidukDAO.addPenduduk(penduduk);			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		model.addAttribute("nik",digitNIK3);
		return "success-tambah-penduduk";
	}
	//////////
	
	/*Soal 4*/
	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		model.addAttribute("keluarga",keluarga);
		return "form-tambah-keluarga";
	}
	
	@PostMapping("/keluarga/tambah")
	public String viewTambahKeluarga(@ModelAttribute KeluargaModel keluarga, @RequestParam(value="nama_kelurahan") String nama_kelurahan, @RequestParam(value="nama_kecamatan") String nama_kecamatan, @RequestParam(value="nama_kota") String nama_kota, Model model) {
		KelurahanModel kelurahan = sidukDAO.selectKelurahan(null,nama_kelurahan);
		if(kelurahan == null) {
			return "not-found";
		}
		keluarga.setId_kelurahan(kelurahan.getId());
		System.out.println(keluarga.getId_kelurahan());
		
		// check hubungan kelurahan-kecamatan yang di-input
		KecamatanModel kecamatan = sidukDAO.selectKecamatan(kelurahan.id_kecamatan,null);
		if(!kecamatan.getNama_kecamatan().equalsIgnoreCase(nama_kecamatan)) {
			return "not-found";
		}

		// check hubungan kecamatan-kota yang di-input
		KotaModel kota = sidukDAO.selectKota(kecamatan.id_kota,null);
		if(!kota.getNama_kota().equalsIgnoreCase(nama_kota)) {
			return "not-found";
		}
		
		// untuk mendapatkan nkk
		String idKecamatan = kecamatan.getKode_kecamatan();
		String digitNKK1 = idKecamatan.substring(0, idKecamatan.length()-1);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String[] dateKeluarga = dtf.format(now).split("-");
		String digitNKK2 = digitNKK1 + dateKeluarga[2] + dateKeluarga[1] + dateKeluarga[0].substring(dateKeluarga[0].length()-2, dateKeluarga[0].length());
		
		String digitNKK3 = sidukDAO.getNkkKeluarga(digitNKK2);
		keluarga.setNomor_kk(digitNKK3);
		
		// counting max id keluarga
		BigInteger jmlKeluarga = new BigInteger(sidukDAO.hitungJmlKeluarga());
		BigInteger jmlKeluargaCounter = new BigInteger("1");
		jmlKeluarga = jmlKeluarga.add(jmlKeluargaCounter);
		keluarga.setId(jmlKeluarga);
		
		// untuk testing status dalam keluarga
		System.out.println("Keluarga " + keluarga);
				
		// tambah keluarga
		try {
			sidukDAO.addKeluarga(keluarga);
			model.addAttribute("nkk",digitNKK3);
			return "success-tambah-keluarga";
		} catch (Exception e) {
			System.out.println(e);
			return "not-found";
		}
	}
	//////////
	
	/*Soal 5*/
	@RequestMapping("/penduduk/ubah/{NIK}")
	public String ubahPenduduk(Model model, @PathVariable(value = "NIK") String nik) {
		
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		if(penduduk == null) {
			return "not-found";
		}
		model.addAttribute("penduduk", penduduk);
		return "form-ubah-penduduk";
	}
	
	@PostMapping("/penduduk/ubah/{NIK}")
	public String viewUbahPenduduk(Model model, @PathVariable(value = "NIK") String nik, @ModelAttribute PendudukModel penduduk) {
		// object penduduk memiliki id=null, nik=null
		PendudukModel pendudukLama = sidukDAO.selectPenduduk(nik);
		penduduk.setId(pendudukLama.getId());
		String digitNIK1 = pendudukLama.getNik().substring(6,12);
		String digitNIK2 = pendudukLama.getNik().substring(0,6);
		String digitNIK3 = "";
		boolean tgl_check = false;
		boolean id_check = false;
				
		if(!penduduk.getTanggal_lahir().equalsIgnoreCase(pendudukLama.getTanggal_lahir())) {
			String[] datePenduduk = penduduk.getTanggal_lahir().split("-");
			digitNIK1 = datePenduduk[2] + datePenduduk[1] + datePenduduk[0].substring(datePenduduk[0].length()-2, datePenduduk[0].length());
			if(penduduk.getJenis_kelamin()==1) {
				digitNIK1 = (Integer.parseInt(digitNIK1.substring(0,1))+4) + digitNIK1.substring(1,digitNIK1.length());
			}
			tgl_check = true;
		}
		
		if(penduduk.id_keluarga != pendudukLama.getId_keluarga()) {
			KeluargaModel keluarga = sidukDAO.selectKeluarga(penduduk.id_keluarga, null);
			if(keluarga == null) {
				return "not-found";
			}
			KelurahanModel kelurahan = sidukDAO.selectKelurahan(keluarga.id_kelurahan,null);
			if(kelurahan == null) {
				return "not-found";
			}
			KecamatanModel kecamatan = sidukDAO.selectKecamatan(kelurahan.id_kecamatan,null);
			if(kecamatan == null) {
				return "not-found";
			}
			
			String idKecamatan = kecamatan.getKode_kecamatan();
			digitNIK2 = idKecamatan.substring(0, idKecamatan.length()-1);
			
			id_check=true;
		}
		
		if((tgl_check || id_check) && !(digitNIK2+digitNIK1).equalsIgnoreCase(pendudukLama.getNik().substring(0,12))) {
			digitNIK3 = sidukDAO.getNikPenduduk(digitNIK2+digitNIK1,penduduk.getJenis_kelamin());
			penduduk.setNik(digitNIK3);
		} else {
			penduduk.setNik(pendudukLama.getNik());
		}
		
		try {
			sidukDAO.updatePenduduk(penduduk);
			model.addAttribute("nik", nik);
			return "success-ubah-penduduk";			
		} catch (Exception e) {
			return "not-found";
		}
	}
	//////////
	
	/*Soal 6*/
	@RequestMapping("/keluarga/ubah/{NKK}")
	public String ubahKeluarga(Model model, @PathVariable(value = "NKK") String nkk) {
		try {
			KeluargaModel keluarga = sidukDAO.selectKeluarga(null, nkk);
			KelurahanModel kelurahan = sidukDAO.selectKelurahan(keluarga.getId_kelurahan(),null);
			KecamatanModel kecamatan = sidukDAO.selectKecamatan(kelurahan.getId_kecamatan(),null);
			KotaModel kota = sidukDAO.selectKota(kecamatan.getId_kota(),null);
			System.out.println("test"+keluarga);
			
			if(keluarga == null || kelurahan == null || kecamatan == null || kota == null) {
				return "not-found";
			}
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("nama_kelurahan",kelurahan.getNama_kelurahan());
			model.addAttribute("nama_kecamatan",kecamatan.getNama_kecamatan());
			model.addAttribute("nama_kota",kota.getNama_kota());
			return "form-ubah-keluarga";
		} catch (Exception e) {
			System.out.println(e);
		}
		return "not-found";
	}
	
	@PostMapping("/keluarga/ubah/{NKK}")
	public String viewUbahKeluarga(Model model, @PathVariable(value = "NKK") String nkk, @ModelAttribute KeluargaModel keluarga, @RequestParam(value="nama_kelurahan") String nama_kelurahan, @RequestParam(value="nama_kecamatan") String nama_kecamatan, @RequestParam(value="nama_kota") String nama_kota) {
		// objek keluarga memiliki id=null,nomor_kk=null, dan id_kelurahan=null
		KeluargaModel keluargaLama = sidukDAO.selectKeluarga(null, nkk);
		KelurahanModel kelurahanLama = sidukDAO.selectKelurahan(keluargaLama.getId_kelurahan(),null);
		KecamatanModel kecamatanLama = sidukDAO.selectKecamatan(kelurahanLama.getId_kecamatan(),null);
		KotaModel kotaLama = sidukDAO.selectKota(kecamatanLama.getId_kota(),null);
		keluarga.setId(keluargaLama.getId());
		
		/* patokan untuk perubahan attribute keluarga*/
		if(keluarga.getAlamat().equalsIgnoreCase(keluargaLama.getAlamat()) && keluarga.getRT().equalsIgnoreCase(keluargaLama.getRT()) && keluarga.getRW().equalsIgnoreCase(keluargaLama.getRW()) && /*keluarga.getId_kelurahan()==keluargaLama.getId_kelurahan() &&*/ nama_kelurahan.equalsIgnoreCase(kelurahanLama.getNama_kelurahan()) && nama_kecamatan.equalsIgnoreCase(kecamatanLama.getNama_kecamatan()) && nama_kota.equalsIgnoreCase(kotaLama.getNama_kota()))/**check bila tidak ada perubahan data pada keluarga*/ {
			return "not-found";
		}
		
		/* buat update nkk*/
		String digitNKK1 = keluargaLama.getNomor_kk().substring(0,6);
		String digitNKK2 = keluargaLama.getNomor_kk().substring(6,12);
		String digitNKK3 = "";
		
		/* karena berhasil melewati if di atas maka pasti ada data yang berubah sehingga perlu meng-update tanggal pembuatan nkk baru*/
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String[] dateKeluarga = dtf.format(now).split("-");
		digitNKK2 = dateKeluarga[2] + dateKeluarga[1] + dateKeluarga[0].substring(dateKeluarga[0].length()-2, dateKeluarga[0].length());
		
		/* set attribute id_kelurahan terhadap keluarga baru*/
		if(!nama_kelurahan.equalsIgnoreCase(kelurahanLama.getNama_kelurahan())) {
			KelurahanModel kelurahanBaru = sidukDAO.selectKelurahan(null, nama_kelurahan);
			KecamatanModel kecamatanBaru = sidukDAO.selectKecamatan(kelurahanBaru.getId_kecamatan(), null);
			KotaModel kotaBaru = sidukDAO.selectKota(kecamatanBaru.getId_kota(), null);
			if(kelurahanBaru!=null && kecamatanBaru!=null && kotaBaru!=null) {
				digitNKK1 = kelurahanBaru.getKode_kelurahan().substring(0,6);
				keluarga.setId_kelurahan(kelurahanBaru.getId());
			} else {
				return "not-found";
			}
		} else {
			keluarga.setId_kelurahan(kelurahanLama.getId());
		}
		
		/* set dan check nkk baru*/
		if(!(digitNKK1+digitNKK2).equalsIgnoreCase(keluargaLama.getNomor_kk().substring(0,12))) {
			digitNKK3 = sidukDAO.getNkkKeluarga(digitNKK1+digitNKK2);
			keluarga.setNomor_kk(digitNKK3);
		} else {
			keluarga.setNomor_kk(keluargaLama.getNomor_kk());
		}
		
		/* update pada database keluarga dan nik penduduk*/
		try {
			sidukDAO.updateKeluarga(keluarga);
			
			KeluargaModel isiKeluarga = sidukDAO.lihatListKeluarga(null, keluarga.getId());
			
			for(int i = 0; i < isiKeluarga.getPenduduk().size(); i++) {
				PendudukModel pendudukLihat = isiKeluarga.getPenduduk().get(i);
				String nik = sidukDAO.getNikPenduduk(digitNKK1+(pendudukLihat.getNik().substring(6,12)), pendudukLihat.getJenis_kelamin());
				pendudukLihat.setNik(nik);;
				sidukDAO.updatePenduduk(pendudukLihat);
			}
			
			// sidukDAO.updateNikPenduduk_Keluarga(digitNKK1,keluarga.getId());
			model.addAttribute("nkk",nkk);
			return "success-ubah-keluarga";			
		} catch (Exception e) {
			System.out.print(e);
			return "not-found";
		}
				
	}
	
	//////////
	
	/*Soal 7*/
	@RequestMapping("/penduduk/{NIK}")
	public String ubahKematian(Model model, @PathVariable(value = "NIK") String nik) {
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		if(penduduk == null) {
			return "not-found";
		}
		model.addAttribute("penduduk", penduduk);
		return "form-ubah-kematian";
	}
	@PostMapping("/penduduk/{NIK}")
	public String viewUbahKematian(Model model, @PathVariable(value = "NIK") String nik, @RequestParam(value="is_wafat")int is_wafat) {
		PendudukModel penduduk1 = sidukDAO.selectPenduduk(nik);
		System.out.println("hehe"+penduduk1);
		penduduk1.setIs_wafat(is_wafat);
		System.out.println("testing"+is_wafat);
		
		sidukDAO.updatePendudukMati(penduduk1.getIs_wafat(), penduduk1.getId());
		
		KeluargaModel keluarga = sidukDAO.lihatListKeluarga(null, penduduk1.id_keluarga);
		
		boolean matiSemua = true;
		
		for(PendudukModel pendudukKeluarga:keluarga.getPenduduk()) {
			if(pendudukKeluarga.getIs_wafat()==0) {
				matiSemua = false;
				break;
			}
		}
		
		if(matiSemua) {
			sidukDAO.ubahBerlakuKeluarga(keluarga.getId());
		}
		
		model.addAttribute("nik",nik);
		
		return "success-ubah-kematian";
	}
	//////////
	
	/*Soal 8*/
	@RequestMapping("/penduduk/cari")
	public String index() {
		List<KotaModel> listKota = sidukDAO.selectListKota();
		model.addAttribute("listKota",listKota);
		return "penduduk-cari";
	}
	///////////
	
}
