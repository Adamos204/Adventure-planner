import { useEffect, useState } from "react"
import {useParams, Link, useNavigate} from "react-router-dom"
import {deleteTraining, getTraining} from "../components/TrainingsFetch.tsx";
import type { Training } from "../types/TrainingTypes"

const fmt = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function TrainingDetails() {
    const { id } = useParams()
    const navigate = useNavigate();
    const [item, setItem] = useState<Training | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [deleted, setDeleted] = useState(false);

    useEffect(() => {
        if (!id) return
        getTraining(id).then(setItem).catch((e) => setError(e instanceof Error ? e.message : "Error"))
    }, [id])

    async function handleDelete() {
        if (!id){
            return;
        }
        if (!confirm("Delete this training session?")){
            return;
        }
        try {
            setDeleted(true);
            await deleteTraining(id);
            navigate(`/trainings/user/${id}`)
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
        } catch (e) {
            alert("Failed to delete training");
            setDeleted(false)
        }
    }

    if (error) {
        return (
            <div className="max-w-2xl mx-auto p-6 space-y-4">
                <p className="text-red-600">{error}</p>
                <Link to="/training" className="text-blue-600 hover:underline">← Back</Link>
            </div>
        )
    }
    if (!item) return <div className="max-w-2xl mx-auto p-6">Loading…</div>

    return (
        <div className="max-w-2xl mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">{item.type}</h1>
            <div className="text-gray-700">{fmt(item.date)} • {item.durationInMin} min • {item.durationInKm} km</div>
            {item.notes && <p className="text-gray-700">Notes: {item.notes}</p>}

            <div className="flex gap-4">
                <Link to={`/training/${item.id}/edit`} className="text-blue-600 hover:underline">Edit</Link>
                <Link to="/training" className="text-blue-600 hover:underline">← Back to list</Link>
                <button onClick={handleDelete} disabled={deleted} className="rounded-full bg-red-500 text-white px-3 py-1.5 shadow hover:bg-red-600 disabled:opacity-60"
                >
                    Delete
                </button>
            </div>
        </div>
    )
}
