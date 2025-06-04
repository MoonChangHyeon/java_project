import { useState } from 'react';

export default function ScanPage() {
  const [project, setProject] = useState('');
  const [file, setFile] = useState<File | null>(null);

  const submit = async () => {
    if (!file) return;
    const form = new FormData();
    form.append('project', project);
    form.append('source', file);
    await fetch('/api/scan', { method: 'POST', body: form });
    alert('Scan requested');
  };

  return (
    <div className="p-4">
      <input className="border m-2" placeholder="Project" value={project} onChange={e => setProject(e.target.value)} />
      <input className="border m-2" type="file" onChange={e => setFile(e.target.files?.[0] || null)} />
      <button className="bg-green-500 text-white px-4 py-2" onClick={submit}>Run Scan</button>
    </div>
  );
}
