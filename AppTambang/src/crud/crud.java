/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
/**
 *
 * @author User
 */
    public class crud {
        private Connection Koneksidb;
        private String username="root";
        private String password="";
        private String dbname="db_tambang";
        private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
        public String CEK_NAMA_KLIEN, CEK_ALAMAT, CEK_TELP_KLIEN, CEK_KODEPOS, CEK_STATUS_KLIEN = null;
        public String CEK_NAMA_BARANG, CEK_SPESIFIKASI, CEK_HARGA_SATUAN = null;
        public String CEK_TGL_PO, CEK_ID_KLIEN, CEK_STATUS_PO, CEK_ID_BARANG, CEK_QTY, CEK_HARGA_PO, CEK_UOM = null;
        public String CEK_NO_PO, CEK_TGL_INVOICE, CEK_JTH_TEMPO, CEK_DISKON, CEK_TOTAL_TAGIHAN = null;
        public boolean duplikasi=false;

    public crud(){
            try {
                Driver dbdriver = new com.mysql.jdbc.Driver();
                DriverManager.registerDriver(dbdriver);
                Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
                System.out.print("Database Terkoneksi");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.toString());
            }
        }
   
//    CRUD KLIEN
    public void simpanKlien01(String id_klien, String nama_klien, String alamat, String telp, String kodepos, String status){
        try {
            String sqlsimpan="insert into klien(id_klien, nama_klien, alamat, telp, kodepos, status) value"
                    + " ('"+id_klien+"', '"+nama_klien+"', '"+alamat+"', '"+telp+"', '"+kodepos+"', '"+status+"')";
            String sqlcari="select*from klien where id_klien='"+id_klien+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Klien sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data klien berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanKlien02(String id_klien, String nama_klien, String alamat, String telp, String kodepos, String status){
        try {
            String sqlsimpan="insert into klien(id_klien, nama_klien, alamat, telp, kodepos, status)"
                    + " value (?, ?, ?, ?, ?, ?)";
            String sqlcari= "select*from klien where id_klien=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_klien);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Klien sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA_KLIEN=data.getString("nama_klien");
                this.CEK_ALAMAT=data.getString("alamat");
                this.CEK_TELP_KLIEN=data.getString("telp");
                this.CEK_KODEPOS=data.getString("kodepos");
                this.CEK_STATUS_KLIEN=data.getString("status");
            } else {
                this.duplikasi=false;
                this.CEK_NAMA_KLIEN=null;
                this.CEK_ALAMAT=null;
                this.CEK_TELP_KLIEN=null;
                this.CEK_KODEPOS=null;
                this.CEK_STATUS_KLIEN=null;
                
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_klien);
                perintah.setString(2, nama_klien);
                perintah.setString(3, alamat);
                perintah.setString(4, telp);
                perintah.setString(5, kodepos);
                perintah.setString(6, status);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data klien berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        
    public void ubahKlien(String id_klien, String nama_klien, String alamat, String telp, String kodepos, String status){
        try {
            String sqlubah="update klien set nama_klien=?, alamat=?, telp=?, kodepos=?, status=? where id_klien=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama_klien);
            perintah.setString(2, alamat);
            perintah.setString(3, telp);
            perintah.setString(4, kodepos);
            perintah.setString(5, status);
            perintah.setString(6, id_klien);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data klien berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        
    public void hapusKlien(String id_klien){
        try {
            String sqlhapus="delete from klien where id_klien=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_klien);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data klien berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
         
    public void tampilDataKlien(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            // Menyesuaikan header kolom
            modeltabel.addColumn("ID Klien");
            modeltabel.addColumn("Nama Klien");
            modeltabel.addColumn("Alamat");
            modeltabel.addColumn("Telepon");
            modeltabel.addColumn("Kode Pos");
            modeltabel.addColumn("Status");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
    }
    
    //    CRUD BARANG
    public void simpanBarang01(String id_barang, String nama_barang, String spesifikasi, String harga_satuan){
        try {
            String sqlsimpan="insert into barang(id_barang, nama_barang, spesifikasi, harga_satuan) value"
                    + " ('"+id_barang+"', '"+nama_barang+"', '"+spesifikasi+"', '"+harga_satuan+"')";
            String sqlcari="select*from barang where id_barang='"+id_barang+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Barang sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data barang berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanBarang02(String id_barang, String nama_barang, String spesifikasi, String harga_satuan){
        try {
            String sqlsimpan="insert into barang(id_barang, nama_barang, spesifikasi, harga_satuan)"
                    + " value (?, ?, ?, ?)";
            String sqlcari= "select*from barang where id_barang=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_barang);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Barang sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA_BARANG=data.getString("nama_barang");
                this.CEK_SPESIFIKASI=data.getString("spesifikasi");
                this.CEK_HARGA_SATUAN=data.getString("harga_satuan");
            } else {
                this.duplikasi=false;
                this.CEK_NAMA_BARANG=null;
                this.CEK_SPESIFIKASI=null;
                this.CEK_HARGA_SATUAN=null;
                
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_barang);
                perintah.setString(2, nama_barang);
                perintah.setString(3, spesifikasi);
                perintah.setString(4, harga_satuan); // JDBC akan mengkonversi String ke DECIMAL
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data barang berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahBarang(String id_barang, String nama_barang, String spesifikasi, String harga_satuan){
        try {
            String sqlubah="update barang set nama_barang=?, spesifikasi=?, harga_satuan=? where id_barang=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama_barang);
            perintah.setString(2, spesifikasi);
            perintah.setString(3, harga_satuan);
            perintah.setString(4, id_barang);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data barang berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusBarang(String id_barang){
        try {
            String sqlhapus="delete from barang where id_barang=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_barang);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data barang berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataBarang(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            // Menyesuaikan header kolom
            modeltabel.addColumn("ID Barang");
            modeltabel.addColumn("Nama Barang");
            modeltabel.addColumn("Spesifikasi");
            modeltabel.addColumn("Harga Satuan");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }
    
//    CRUD PURCHASE ORDER
    public void simpanPO01(String no_po, String tgl_po, String id_klien, String status_po, String id_barang, String qty, String harga, String UoM){
        try {
            String sqlsimpan="insert into purchase_order(no_po, tgl_po, id_klien, status_po, id_barang, qty, harga, UoM) value"
                    + " ('"+no_po+"', '"+tgl_po+"', '"+id_klien+"', '"+status_po+"', '"+id_barang+"', '"+qty+"', '"+harga+"', '"+UoM+"')";
            String sqlcari="select*from purchase_order where no_po='"+no_po+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "No PO sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data PO berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanPO02(String no_po, String tgl_po, String id_klien, String status_po, String id_barang, String qty, String harga, String UoM){
        try {
            String sqlsimpan="insert into purchase_order(no_po, tgl_po, id_klien, status_po, id_barang, qty, harga, UoM)"
                    + " value (?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "select*from purchase_order where no_po=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, no_po);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "No PO sudah terdaftar");
                this.duplikasi=true;
                this.CEK_TGL_PO=data.getString("tgl_po");
                this.CEK_ID_KLIEN=data.getString("id_klien");
                this.CEK_STATUS_PO=data.getString("status_po");
                this.CEK_ID_BARANG=data.getString("id_barang");
                this.CEK_QTY=data.getString("qty");
                this.CEK_HARGA_PO=data.getString("harga");
                this.CEK_UOM=data.getString("UoM");
            } else {
                this.duplikasi=false;
                this.CEK_TGL_PO=null;
                this.CEK_ID_KLIEN=null;
                this.CEK_STATUS_PO=null;
                this.CEK_ID_BARANG=null;
                this.CEK_QTY=null;
                this.CEK_HARGA_PO=null;
                this.CEK_UOM=null;
                
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, no_po);
                perintah.setString(2, tgl_po); // JDBC akan mengkonversi String "YYYY-MM-DD" ke DATE
                perintah.setString(3, id_klien);
                perintah.setString(4, status_po);
                perintah.setString(5, id_barang);
                perintah.setString(6, qty); // JDBC akan mengkonversi String ke INT
                perintah.setString(7, harga); // JDBC akan mengkonversi String ke DECIMAL
                perintah.setString(8, UoM);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data PO berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahPO(String no_po, String tgl_po, String id_klien, String status_po, String id_barang, String qty, String harga, String UoM){
        try {
            String sqlubah="update purchase_order set tgl_po=?, id_klien=?, status_po=?, id_barang=?, qty=?, harga=?, UoM=? where no_po=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, tgl_po);
            perintah.setString(2, id_klien);
            perintah.setString(3, status_po);
            perintah.setString(4, id_barang);
            perintah.setString(5, qty);
            perintah.setString(6, harga);
            perintah.setString(7, UoM);
            perintah.setString(8, no_po);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data PO berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusPO(String no_po){
        try {
            String sqlhapus="delete from purchase_order where no_po=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, no_po);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data PO berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataPO(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            // Menyesuaikan header kolom
            modeltabel.addColumn("No PO");
            modeltabel.addColumn("Tgl PO");
            modeltabel.addColumn("ID Klien");
            modeltabel.addColumn("Status PO");
            modeltabel.addColumn("ID Barang");
            modeltabel.addColumn("Qty");
            modeltabel.addColumn("Harga");
            modeltabel.addColumn("UoM");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }
    
//    CRUD INVOICE
    public void simpanInvoice01(String no_invoice, String no_po, String tgl_invoice, String tgl_jth_tempo, String diskon, String total_tagihan){
        try {
            String sqlsimpan="insert into invoice(no_invoice, no_po, tgl_invoice, tgl_jth_tempo, diskon, total_tagihan) value"
                    + " ('"+no_invoice+"', '"+no_po+"', '"+tgl_invoice+"', '"+tgl_jth_tempo+"', '"+diskon+"', '"+total_tagihan+"')";
            String sqlcari="select*from invoice where no_invoice='"+no_invoice+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "No Invoice sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data invoice berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanInvoice02(String no_invoice, String no_po, String tgl_invoice, String tgl_jth_tempo, String diskon, String total_tagihan){
        try {
            String sqlsimpan="insert into invoice(no_invoice, no_po, tgl_invoice, tgl_jth_tempo, diskon, total_tagihan)"
                    + " value (?, ?, ?, ?, ?, ?)";
            String sqlcari= "select*from invoice where no_invoice=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, no_invoice);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "No Invoice sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NO_PO=data.getString("no_po");
                this.CEK_TGL_INVOICE=data.getString("tgl_invoice");
                this.CEK_JTH_TEMPO=data.getString("tgl_jth_tempo");
                this.CEK_DISKON=data.getString("diskon");
                this.CEK_TOTAL_TAGIHAN=data.getString("total_tagihan");
            } else {
                this.duplikasi=false;
                this.CEK_NO_PO=null;
                this.CEK_TGL_INVOICE=null;
                this.CEK_JTH_TEMPO=null;
                this.CEK_DISKON=null;
                this.CEK_TOTAL_TAGIHAN=null;
                
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, no_invoice);
                perintah.setString(2, no_po);
                perintah.setString(3, tgl_invoice);
                perintah.setString(4, tgl_jth_tempo);
                perintah.setString(5, diskon);
                perintah.setString(6, total_tagihan);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data invoice berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahInvoice(String no_invoice, String no_po, String tgl_invoice, String tgl_jth_tempo, String diskon, String total_tagihan){
        try {
            String sqlubah="update invoice set no_po=?, tgl_invoice=?, tgl_jth_tempo=?, diskon=?, total_tagihan=? where no_invoice=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, no_po);
            perintah.setString(2, tgl_invoice);
            perintah.setString(3, tgl_jth_tempo);
            perintah.setString(4, diskon);
            perintah.setString(5, total_tagihan);
            perintah.setString(6, no_invoice);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data invoice berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusInvoice(String no_invoice){
        try {
            String sqlhapus="delete from invoice where no_invoice=?";           
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, no_invoice);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data invoice berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataInvoice(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            // Menyesuaikan header kolom
            modeltabel.addColumn("No Invoice");
            modeltabel.addColumn("No PO");
            modeltabel.addColumn("Tgl Invoice");
            modeltabel.addColumn("Jatuh Tempo");
            modeltabel.addColumn("Diskon");
            modeltabel.addColumn("Total Tagihan");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }
    }
    
