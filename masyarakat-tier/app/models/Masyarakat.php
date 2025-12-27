<?php
class Masyarakat extends Model {
    
    public $id;
    public $noKtp;
    public $nama;
    public $alamat;

    public function __construct($db) {
        parent::__construct($db);
        $this->table = "masyarakats";
    }

    public function getAll() {
        $query = "SELECT * FROM {$this->table} ORDER BY id ASC";
        return $this->executeQuery($query);
    }

    public function getById() {
        $query = "SELECT * FROM {$this->table} WHERE id = :id LIMIT 1";
        $stmt = $this->executeQuery($query, [':id' => $this->id]);
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($row) {
            $this->noKtp = $row['noKtp'];
            $this->nama = $row['nama'];
            $this->alamat = $row['alamat'];
            return $row;
        }

        return false;
    }

    public function create() {
        $query = "INSERT INTO {$this->table} 
                  (noKtp, nama, alamat) 
                  VALUES (:noKtp, :nama, :alamat)";

        $params = [
            ':noKtp' => $this->noKtp,
            ':nama' => $this->nama,
            ':alamat' => $this->alamat
        ];

        $stmt = $this->executeQuery($query, $params);

        if ($stmt) {
            $this->id = $this->conn->lastInsertId();
            return true;
        }

        return false;
    }

    public function update() {
        $query = "UPDATE {$this->table} 
                  SET nim = :nim, 
                      nama = :nama, 
                      jurusan = :jurusan
                  WHERE id = :id";

        $params = [
            ':id' => $this->id,
            ':noKtp' => $this->noKtp,
            ':nama' => $this->nama,
            ':alamat' => $this->alamat
        ];

        $stmt = $this->executeQuery($query, $params);
        return $stmt->rowCount() > 0;
    }

    public function delete() {
        $query = "DELETE FROM {$this->table} WHERE id = :id";
        $stmt = $this->executeQuery($query, [':id' => $this->id]);
        return $stmt->rowCount() > 0;
    }
}
