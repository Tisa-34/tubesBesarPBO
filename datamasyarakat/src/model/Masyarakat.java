package model;

public class Masyarakat {
    private int id;
    private String noKtp;
    private String nama;
    private String alamat;

    public Masyarakat(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNim(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
