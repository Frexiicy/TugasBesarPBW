document.addEventListener("DOMContentLoaded", function () {
    // Ambil elemen yang berisi data untuk grafik
    var grafikDataElement = document.getElementById("grafikData");

    // Pastikan elemen grafikData ada
    if (!grafikDataElement) {
        console.error("Elemen grafikData tidak ditemukan di halaman.");
        return;
    }

    // Parsing data-labels dan data-data dari elemen HTML
    var labels = [];
    var data = [];
    try {
        labels = JSON.parse(grafikDataElement.getAttribute("data-labels") || "[]");
        data = JSON.parse(grafikDataElement.getAttribute("data-data") || "[]");

        // Debugging data
        console.log("Labels yang diterima:", labels);
        console.log("Data yang diterima:", data);

        // Periksa jika data kosong
        if (labels.length === 0 || data.length === 0) {
            console.warn("Data grafik kosong. Pastikan data sudah dikirim dari server.");
        }
    } catch (error) {
        console.error("Kesalahan saat mem-parsing data grafik:", error);
    }

    // Pastikan elemen canvas untuk grafik tersedia
    var chartCanvas = document.getElementById("rentalChart");
    if (!chartCanvas) {
        console.error("Elemen canvas untuk grafik (rentalChart) tidak ditemukan.");
        return;
    }

    // Buat grafik menggunakan Chart.js
    var ctx = chartCanvas.getContext("2d");
    var rentalChart = new Chart(ctx, {
        type: "line", 
        data: {
            labels: labels, // Label sumbu X
            datasets: [{
                label: "Jumlah Penyewaan", // Label data
                data: data, // Data sumbu Y
                backgroundColor: "rgba(75, 192, 192, 0.6)", // Warna batang
                borderColor: "rgba(75, 192, 192, 1)", // Warna garis batang
                borderWidth: 1,
            }],
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    position: "top",
                },
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: "Bulan", // Judul sumbu X
                    },
                },
                y: {
                    title: {
                        display: true,
                        text: "Jumlah Penyewaan", // Judul sumbu Y
                    },
                    beginAtZero: true,
                },
            },
        },
    });
});


// Fungsi untuk mengunduh laporan PDF
function downloadPDF() {
    const { jsPDF } = window.jspdf;
    const pdf = new jsPDF();

    // Judul laporan
    pdf.setFontSize(16);
    pdf.text("Laporan Penyewaan Film", 10, 10);

    // Data tabel
    pdf.setFontSize(12);
    let y = 20;
    pdf.text("Tabel Penyewaan:", 10, y);
    y += 10;

    const headers = ["Judul Film", "Tanggal Sewa", "Tanggal Kembali"];
    const rows = /*[[${laporanPeminjaman}]]*/ [];

    rows.forEach((row) => {
        pdf.text(`${row.judul} | ${row.tanggalSewa} | ${row.tanggalKembali}`, 10, y);
        y += 6;
    });

    // Grafik penyewaan
    y += 10;
    pdf.text("Grafik Penyewaan:", 10, y);
    y += 10;

    const canvas = document.getElementById("rentalChart");
    const imgData = canvas.toDataURL("image/png");
    pdf.addImage(imgData, "PNG", 10, y, 190, 100);

    // Simpan PDF
    pdf.save("Laporan_Penyewaan_Film.pdf");
}