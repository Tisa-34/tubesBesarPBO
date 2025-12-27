<?php
class MasyarakatController extends Controller {
    private $service;

    public function __construct() {
        $db = (new Database())->getConnection();
        $masyarakatModel = new Masyarakat($db);
        $this->service = new MasyarakatService($masyarakatModel);
    }

    public function index() {
        try {
            $result = $this->service->getAll();
            $this->success($result, 'Data masyarakat berhasil diambil');
        } catch (Exception $e) {
            $this->error('Gagal mengambil data: ' . $e->getMessage(), 500);
        }
    }

    public function show($id) {
        try {
            $result = $this->service->getById((int)$id);
            if ($result) {
                $this->success($result, 'Data masyarakat ditemukan');
            } else {
                $this->error('Data masyarakat tidak ditemukan', 404);
            }
        } catch (Exception $e) {
            $this->error('Gagal mengambil data: ' . $e->getMessage(), 500);
        }
    }

    public function create() {
        $input = $this->getJsonInput();
        if (!$input) {
            $this->error('Data JSON tidak valid', 400);
        }
        try {
            $created = $this->service->create($input);
            $this->success($created, 'Masyarakat berhasil ditambahkan', 201);
        } catch (Exception $e) {
            $this->error('Error: ' . $e->getMessage(), 500);
        }
    }

    public function update($id) {
        if (!$id || !is_numeric($id)) {
            $this->error('ID tidak valid', 400);
        }
        $input = $this->getJsonInput();
        if (!$input) {
            $this->error('Data JSON tidak valid', 400);
        }
        try {
            $this->service->update((int)$id, $input);
            $this->success(null, 'Data masyarakat berhasil diperbarui');
        } catch (Exception $e) {
            $this->error('Error: ' . $e->getMessage(), 500);
        }
    }

    public function delete($id) {
        if (!$id || !is_numeric($id)) {
            $this->error('ID tidak valid', 400);
        }
        try {
            $this->service->delete((int)$id);
            $this->success(null, 'Masyarakat berhasil dihapus');
        } catch (Exception $e) {
            $this->error('Error: ' . $e->getMessage(), 500);
        }
    }
}
