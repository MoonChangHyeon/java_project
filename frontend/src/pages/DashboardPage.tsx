import { useEffect, useState } from 'react';

interface Vulnerability {
  id: number;
  cweId: string;
  severity: string;
  filePath: string;
}

export default function DashboardPage() {
  const [data, setData] = useState<Vulnerability[]>([]);

  useEffect(() => {
    fetch('/api/vulnerabilities')
      .then(res => res.json())
      .then(setData);
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-xl mb-4">Vulnerabilities</h1>
      <table className="min-w-full text-sm">
        <thead>
          <tr>
            <th className="border p-2">CWE</th>
            <th className="border p-2">Severity</th>
            <th className="border p-2">Path</th>
          </tr>
        </thead>
        <tbody>
          {data.map(v => (
            <tr key={v.id}>
              <td className="border p-2">{v.cweId}</td>
              <td className="border p-2">{v.severity}</td>
              <td className="border p-2">{v.filePath}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
