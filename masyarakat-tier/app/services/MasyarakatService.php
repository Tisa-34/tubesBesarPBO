<?php

class MasyarakatService {
    private $masyarakat; // DAO/Repository

    public function __construct(Masyarakat $masyarakat) {
        $this->masyarakat = $masyarakat;
    }

    public function getAll() {
        $stmt = $this->masyarakat->getAll();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getById(int $id) {
        $this->masyarakat->id = $id;
        return $this->masyarakat->getById();
    }

    public function create(array $input) {
        $this->validateRequired($input, ['nim', 'nama']);
        $input = $this->sanitize($input);
        $this->masyarakat->noKtp = $input['noKtp'];
        $this->masyarakat->nama = $input['nama'];
        $this->masyarakat->alamat = $input['alamat'] ?? null;
        if ($this->masyarakat->create()) {
            return [
                'id' => $this->masyarakat->id,
                'noKtp' => $this->masyarakat->noKtp,
                'nama' => $this->masyarakat->nama,
                'alamat' => $this->masyarakat->alamat
            ];
        }
        throw new Exception('Gagal menambahkan masyarakat');
    }

    public function update(int $id, array $input) {
        $this->validateRequired($input, ['nim', 'nama']);
        $input = $this->sanitize($input);
        $this->masyarakat->id = $id;
        $this->masyarakat->noKtp = $input['noKtp'];
        $this->masyarakat->nama = $input['nama'];
        $this->masyarakat->alamat = $input['alamat'] ?? null;
        if (!$this->masyarakat->update()) {
            throw new Exception('Gagal memperbarui data atau data tidak ditemukan');
        }
    }

    public function delete(int $id) {
        $this->masyarakat->id = $id;
        if (!$this->masyarakat->delete()) {
            throw new Exception('Gagal menghapus data atau data tidak ditemukan');
        }
    }

    private function validateRequired(array $input, array $requiredFields): void {
        $missing = [];
        foreach ($requiredFields as $field) {
            if (!isset($input[$field]) || empty(trim($input[$field]))) {
                $missing[] = $field;
            }
        }
        if (!empty($missing)) {
            throw new Exception('Field wajib: ' . implode(', ', $missing));
        }
    }

    private function sanitize($data) {
        if (is_array($data)) {
            return array_map([$this, 'sanitize'], $data);
        }
        return htmlspecialchars(strip_tags(trim($data)), ENT_QUOTES, 'UTF-8');
    }
}
