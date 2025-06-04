export default function ReportPage() {
  const download = () => {
    window.location.href = '/api/report';
  };
  return (
    <div className="p-4">
      <button className="bg-purple-600 text-white px-4 py-2" onClick={download}>Download XLSX</button>
    </div>
  );
}
