-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 12 Sep 2021 pada 18.24
-- Versi server: 10.4.16-MariaDB
-- Versi PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasir`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `Kode_Barang` varchar(10) NOT NULL,
  `Nama_Barang` text NOT NULL,
  `Stok` int(11) NOT NULL,
  `Satuan` text NOT NULL,
  `Harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`Kode_Barang`, `Nama_Barang`, `Stok`, `Satuan`, `Harga`) VALUES
('BG1', 'Bawang Goreng', 3, '200gr', 50000),
('BM1', 'Bawang Merah', 7, '1kg', 35000),
('DS1', 'Dodol Sirsak', 35, '25gr', 25000),
('GA1', 'Gula Aren UK', 15, '1kg', 50000),
('GB1', 'Gula Batu', 49, '500gr', 20000),
('GG1', 'Getuk Goreng', 28, '250gr', 20000),
('KT1', 'Keripik Tahu', 14, '500gr', 25000),
('MC1', 'Manisan Carica', 29, '6cup', 30000),
('MC2', 'Manisan Carica', 26, '12cup', 55000),
('PS1', 'Pie Susu', 65, '27gr', 40000),
('TA1', 'Telur Asin', 97, 'butir', 4000),
('TAB1', 'Telur Asin Bakar', 73, 'butir', 4000),
('TEH1', 'Teh Poci', 45, 'pack', 10000),
('TEH2', 'Teh Gopek', 16, 'pack', 10000),
('TEH3', 'Teh Tong Ji', 46, 'pack', 10000),
('TEH4', 'Teh Dandang', 2, 'pack', 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_barang`
--

CREATE TABLE `detail_barang` (
  `Kode_Detail` varchar(10) NOT NULL,
  `Kode_Barang` varchar(10) NOT NULL,
  `Harga` int(11) NOT NULL,
  `Jumlah` int(11) NOT NULL,
  `Subtotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detail_barang`
--

INSERT INTO `detail_barang` (`Kode_Detail`, `Kode_Barang`, `Harga`, `Jumlah`, `Subtotal`) VALUES
('DTRX001', 'BG1', 50000, 2, 100000),
('DTRX001', 'BM1', 35000, 2, 70000),
('DTRX001', 'GA1', 50000, 2, 100000),
('DTRX001', 'DS1', 25000, 2, 50000),
('DTRX002', 'BG1', 50000, 20, 1000000),
('DTRX002', 'KT1', 25000, 2, 50000),
('DTRX002', 'TAB1', 4000, 2, 8000),
('DTRX003', 'BM1', 35000, 2, 70000),
('DTRX004', 'DS1', 25000, 2, 50000),
('DTRX005', 'TAB1', 4000, 22, 88000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `login`
--

CREATE TABLE `login` (
  `UserId` varchar(5) NOT NULL,
  `Username` varchar(10) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `Role` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `login`
--

INSERT INTO `login` (`UserId`, `Username`, `Password`, `Role`) VALUES
('A0001', 'admin', 'admin', 'admin'),
('U0001', 'kasir1', '1234', 'kasir'),
('U0002', 'kasir2', '2222', 'kasir'),
('U0003', 'kasir3', '4321', 'kasir');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `Kode_Transaksi` varchar(10) NOT NULL,
  `Kode_Detail` varchar(10) NOT NULL,
  `Tanggal` date NOT NULL,
  `Jam` time NOT NULL,
  `Total` int(11) NOT NULL,
  `LastUpdate_by` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`Kode_Transaksi`, `Kode_Detail`, `Tanggal`, `Jam`, `Total`, `LastUpdate_by`) VALUES
('TRX001', 'DTRX001', '2021-09-07', '12:01:05', 320000, 'A0001'),
('TRX002', 'DTRX002', '2021-09-07', '13:16:48', 1058000, 'A0001'),
('TRX003', 'DTRX003', '2021-09-07', '14:10:19', 70000, 'U0001'),
('TRX004', 'DTRX004', '2021-09-07', '14:10:35', 50000, 'U0002'),
('TRX005', 'DTRX005', '2021-09-07', '14:10:57', 88000, 'U0003');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`Kode_Barang`);

--
-- Indeks untuk tabel `detail_barang`
--
ALTER TABLE `detail_barang`
  ADD KEY `Kode_Barang` (`Kode_Barang`),
  ADD KEY `Kode_Detail` (`Kode_Detail`);

--
-- Indeks untuk tabel `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`UserId`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`Kode_Transaksi`),
  ADD KEY `LastUpdate_by` (`LastUpdate_by`),
  ADD KEY `Kode_Detail` (`Kode_Detail`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_barang`
--
ALTER TABLE `detail_barang`
  ADD CONSTRAINT `detail_barang_ibfk_1` FOREIGN KEY (`Kode_Barang`) REFERENCES `barang` (`Kode_Barang`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`LastUpdate_by`) REFERENCES `login` (`UserId`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`Kode_Detail`) REFERENCES `detail_barang` (`Kode_Detail`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
