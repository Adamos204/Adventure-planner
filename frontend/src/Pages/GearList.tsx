import { useEffect, useState } from "react"
import { Link, useParams } from "react-router-dom"
import { deleteGear, listGear, updateGear } from "../components/GearsFetch.tsx"
import type {GearItem} from "../types/GearTypes.ts";

export default function GearList() {
    const { adventureId } = useParams()
    const [items, setItems] = useState<GearItem[] | null>(null)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        if (!adventureId) return
        listGear(adventureId).then(setItems).catch((e) => {
            setError(e instanceof Error ? e.message : "Error")
            setItems([])
        })
    }, [adventureId])

    async function onToggle(item: GearItem) {
        try {
            const updated = await updateGear(item.id, {
                name: item.name,
                quantity: item.quantity,
                packed: !item.packed,
            })
            setItems(prev => prev?.map(i => i.id === item.id ? updated : i) ?? null)
        } catch {
            alert("Failed to update packed state")
        }
    }


    async function onDelete(id: number) {
        if (!confirm("Delete this item?")) return
        await deleteGear(id)
        setItems((prev) => prev?.filter(i => i.id !== id) ?? null)
    }

    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              Gear Items
            </span>
                    </h1>
                    <p className="mt-2 text-gray-600">Manage your checklist for this adventure.</p>
                </div>
            </section>

            <section>
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <div className="text-lg font-semibold text-gray-900">All Gear</div>
                            <Link
                                to={`/adventures/${adventureId}/gear/new`}
                                className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                            >
                                Add Item
                            </Link>
                        </div>

                        {!items && !error && <div className="px-6 py-6 text-gray-600">Loading…</div>}
                        {error && <div className="px-6 py-6 text-red-600">{error}</div>}

                        {items && (
                            <ul className="divide-y">
                                {items.length === 0 ? (
                                    <li className="px-6 py-6 text-gray-600">No gear yet.</li>
                                ) : (
                                    items.map((g) => (
                                        <li key={g.id} className="px-6 py-3 flex items-center justify-between">
                                            <div className="flex items-center gap-3">
                                                <input
                                                    type="checkbox"
                                                    checked={g.packed}
                                                    onChange={() => onToggle(g)}
                                                    className="h-5 w-5"
                                                />

                                                <div>
                                                    <div className="font-medium text-gray-900">{g.name}</div>
                                                    <div className="text-sm text-gray-600">Qty: {g.quantity}</div>
                                                </div>
                                            </div>

                                            <div className="flex items-center gap-4">
                                                <Link to={`/adventures/${adventureId}/gear/${g.id}/edit`} className="text-gray-700 text-sm hover:underline">Edit</Link>
                                                <button onClick={() => onDelete(g.id)} className="rounded-full bg-red-500 text-white px-3 py-1.5 text-sm shadow hover:bg-red-600">
                                                    Delete
                                                </button>
                                            </div>
                                        </li>
                                    ))
                                )}
                            </ul>
                        )}
                    </div>

                    <div className="mt-6">
                        <Link to={`/adventures/${adventureId}`} className="text-blue-600 hover:underline">
                            ← Back to adventure
                        </Link>
                    </div>
                </div>
            </section>
        </main>
    )
}