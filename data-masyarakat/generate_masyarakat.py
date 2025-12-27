import random
from faker import Faker

fake = Faker('id_ID')  # Menggunakan lokal Indonesia untuk nama yang lebih relevan
num_records = 500000
file_name = 'masyarakat_data_500k.sql'

# Alamat (nama-nama kota di Indonesia)
cities = [
    'Jakarta', 'Bandung', 'Surabaya', 'Medan', 'Semarang', 'Yogyakarta',
    'Malang', 'Denpasar', 'Makassar', 'Balikpapan', 'Samarinda',
    'Banjarmasin', 'Pontianak', 'Palembang', 'Pekanbaru', 'Padang',
    'Batam', 'Bogor', 'Depok', 'Bekasi', 'Tangerang', 'Cirebon',
    'Tasikmalaya', 'Purwokerto', 'Solo', 'Magelang', 'Kediri',
    'Madiun', 'Blitar', 'Probolinggo', 'Pasuruan', 'Gresik',
    'Lamongan', 'Tuban', 'Bojonegoro', 'Ngawi', 'Ponorogo',
    'Pacitan', 'Trenggalek', 'Tulungagung'
]

with open(file_name, 'w', encoding='utf-8') as f:
    # 1. Perintah DDL
    f.write("CREATE DATABASE IF NOT EXISTS realtime_db;\n")
    f.write("USE realtime_db;\n")
    f.write("CREATE TABLE IF NOT EXISTS masyarakats (\n")
    f.write("    id INT AUTO_INCREMENT PRIMARY KEY,\n")
    f.write("    noKtp VARCHAR(255) UNIQUE NOT NULL,\n")
    f.write("    nama VARCHAR(255) NOT NULL,\n")
    f.write("    alamat VARCHAR(255) NOT NULL\n")
    f.write(");\n\n")

    # 2. Perintah DML
    f.write("INSERT INTO masyarakats (noKtp, nama, alamat) VALUES\n")

    for i in range(1, num_records + 1):
        noKtp = f"3204{i:012d}"
        
        # Menggunakan first_name() dan last_name() dari lokal id_ID
        nama = f"{fake.first_name()} {fake.last_name()}".replace("'", "''")
        
        alamat = random.choice(cities)

        line = f"('{noKtp}', '{nama}', '{alamat}')"

        if i < num_records:
            line += ",\n"
        else:
            line += ";\n"

        f.write(line)

print(f"File '{file_name}' dengan {num_records} data masyarakat Indonesia telah berhasil dibuat.")
