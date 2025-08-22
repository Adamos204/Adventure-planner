import { useEffect, useState } from "react"
import {useParams, Link, useNavigate} from "react-router-dom"
import {deleteAdventure, getAdventure} from "../components/AdventuresFecth.tsx";
import type { Adventure } from "../types/AdventureTypes.ts";
import {listGear} from "../components/GearsFetch.tsx";
import type {GearItem} from "../types/GearTypes.ts";
import {useCurrentUserId} from "../helper/UseCurrentUserId.ts";

const fmt = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function AdventureDetails() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState<Adventure | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [deleted, setDeleted] = useState(false);
    const [gear, setGear] = useState<GearItem[] | null>(null)
    const { userId } = useCurrentUserId();

    useEffect(() => {
        if (!id) return
        getAdventure(id)
            .then(setItem)
            .catch((e) => setError(e instanceof Error ? e.message : "Error"))
    }, [id])

    useEffect(() => {
        if (!id) return
        listGear(id).then((g) => setGear(g)).catch(() => setGear([]))
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
            await deleteAdventure(id);
            navigate(`/adventures/user/${userId}`)
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
                <Link to={`/adventures/user/${userId}`} className="text-blue-600 hover:underline">← Back to list</Link>
                <button onClick={handleDelete} disabled={deleted} className="rounded-full bg-red-500 text-white px-3 py-1.5 shadow hover:bg-red-600 disabled:opacity-60"
                >
                    Delete
                </button>
            </div>
            <div className="rounded-2xl border bg-white shadow-sm mt-6">
                <div className="flex items-center justify-between px-6 py-4 border-b">
                    <div className="text-lg font-semibold text-gray-900">Gear (top 5)</div>
                    <div className="flex gap-3">
                        <Link to={`/adventures/${id}/gear`} className="rounded-xl border px-3 py-1.5 text-sm hover:bg-gray-50">View all</Link>
                        <Link to={`/adventures/${id}/gear/new`} className="rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm shadow hover:opacity-90">Add item</Link>
                    </div>
                </div>

                {!gear && <div className="px-6 py-6 text-gray-600">Loading…</div>}
                {gear && gear.length === 0 && <div className="px-6 py-6 text-gray-600">No items yet.</div>}

                {gear && gear.length > 0 && (
                    <ul className="divide-y">
                        {gear.slice(0, 5).map((g) => (
                            <li key={g.id} className="px-6 py-3 flex items-center justify-between">
                                <div className="flex items-center gap-3">
                                    <span className={`inline-block h-2.5 w-2.5 rounded-full ${g.packed ? "bg-green-500" : "bg-gray-300"}`} />
                                    <div className="font-medium text-gray-900">{g.name}</div>
                                </div>
                                <div className="text-sm text-gray-600">Qty: {g.quantity}</div>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    )
}
