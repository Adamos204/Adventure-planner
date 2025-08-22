import { useEffect, useState } from "react"
import {useParams, Link, useNavigate} from "react-router-dom"
import { getAdventure } from "../components/AdventuresFecth.tsx";
import type { Adventure } from "../types/AdventureTypes.ts";
import {deleteTraining} from "../components/TrainingsFetch.tsx";

const fmt = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function AdventureDetails() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState<Adventure | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [deleted, setDeleted] = useState(false);

    useEffect(() => {
        if (!id) return
        getAdventure(id)
            .then(setItem)
            .catch((e) => setError(e instanceof Error ? e.message : "Error"))
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
                <p className="text-red-600">{error} dadas</p>
                <Link to="/adventures" className="text-blue-600 hover:underline">← Back</Link>
            </div>
        )
    }

    if (!item) {
        return <div className="max-w-2xl mx-auto p-6">Loading…</div>
    }

    return (
        <div className="max-w-2xl mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">{item.name}</h1>
            <div className="text-gray-700">{fmt(item.date)}{item.location ? ` • ${item.location}` : ""}</div>
            {item.description && <p className="text-gray-700">{item.description}</p>}
            {item.notes && <p className="text-gray-600">Notes: {item.notes}</p>}
            <div className="grid grid-cols-2 gap-4 text-sm text-gray-700">
                {item.lengthInDays != null && <div><span className="text-gray-500">Days:</span> {item.lengthInDays}</div>}
                {item.lengthInKm != null && <div><span className="text-gray-500">Distance:</span> {item.lengthInKm} km</div>}
                {item.startLocation && <div><span className="text-gray-500">Start:</span> {item.startLocation}</div>}
                {item.endLocation && <div><span className="text-gray-500">End:</span> {item.endLocation}</div>}
            </div>

            <div className="flex gap-4">
                <Link to={`/adventures/${item.id}/edit`} className="text-blue-600 hover:underline">Edit</Link>
                <Link to="/dashboard" className="text-blue-600 hover:underline">← Back to list</Link>
                <button onClick={handleDelete} disabled={deleted} className="rounded-full bg-red-500 text-white px-3 py-1.5 shadow hover:bg-red-600 disabled:opacity-60"
                >
                    Delete
                </button>
            </div>
        </div>
    )
}
