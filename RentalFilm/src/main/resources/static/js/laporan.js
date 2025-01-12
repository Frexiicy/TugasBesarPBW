// Data untuk grafik penyewaan
var grafikDataElement = document.getElementById("grafikData");

var labels = JSON.parse(grafikDataElement.getAttribute("data-labels"));
var data = JSON.parse(grafikDataElement.getAttribute("data-data"));

// Inisialisasi grafik penyewaan
var ctx = document.getElementById("rentalChart").getContext("2d");
var rentalChart = new Chart(ctx, {
    type: "line", // Line chart
    data: {
        labels: labels,
        datasets: [{
            label: "Jumlah Penyewaan",
            data: data,
            borderColor: "rgba(75, 192, 192, 1)",
            backgroundColor: "rgba(75, 192, 192, 0.2)",
            fill: true,
        }],
    },
    options: {
        responsive: true,
        scales: {
            x: {
                title: {
                    display: true,
                    text: "Tanggal",
                },
            },
            y: {
                title: {
                    display: true,
                    text: "Jumlah Penyewaan",
                },
            },
        },
    },
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