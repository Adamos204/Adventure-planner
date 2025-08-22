import { useEffect, useState } from "react"
import { Link, useParams } from "react-router-dom"
import { deleteTravelPlan, listTravelPlans } from "../components/TravelPlanFetch.tsx";
import type { TravelPlan } from "../types/TravelPlanTypes"

export default function TravelPlanList() {
    const { adventureId } = useParams()
    const [items, setItems] = useState<TravelPlan[] | null>(null)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        if (!adventureId) return
        listTravelPlans(adventureId).then(setItems).catch(e => {
            setError(e instanceof Error ? e.message : "Error")
            setItems([])
        })
    }, [adventureId])

    async function onDelete(id: number) {
        if (!confirm("Delete this plan?")) return
        await deleteTravelPlan(id)
        setItems(prev => prev?.filter(p => p.id !== id) ?? null)
    }

    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              Travel Plans
            </span>
                    </h1>
                    <p className="mt-2 text-gray-600">Transport legs for this adventure.</p>
                </div>
            </section>

            <section>
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <div className="text-lg font-semibold text-gray-900">All Plans</div>
                            <Link to={`/adventures/${adventureId}/travel/new`}
                                  className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition">
                                Add Plan
                            </Link>
                        </div>

                        {!items && !error && <div className="px-6 py-6 text-gray-600">Loading…</div>}
                        {error && <div className="px-6 py-6 text-red-600">{error}</div>}

                        {items && (
                            <ul className="divide-y">
                                {items.length === 0 ? (
                                    <li className="px-6 py-6 text-gray-600">No travel plans yet.</li>
                                ) : (
                                    items.map((p) => (
                                        <li key={p.id} className="px-6 py-3 flex items-center justify-between">
                                            <div>
                                                <div className="font-medium text-gray-900">{p.type}</div>
                                                <div className="text-sm text-gray-600">
                                                    {p.departureLocation} ({p.departureTime.slice(0,5)}) → {p.arrivalLocation} ({p.arrivalTime.slice(0,5)})
                                                </div>
                                                {p.notes && <div className="text-sm text-gray-600 mt-1 line-clamp-2">Notes: {p.notes}</div>}
                                            </div>

                                            <div className="flex items-center gap-4">
                                                <Link to={`/adventures/${adventureId}/travel/${p.id}/edit`} className="text-gray-700 text-sm hover:underline">Edit</Link>
                                                <button onClick={() => onDelete(p.id)} className="rounded-full bg-red-500 text-white px-3 py-1.5 text-sm shadow hover:bg-red-600">
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
                        <Link to={`/adventures/${adventureId}`} className="text-blue-600 hover:underline">← Back to adventure</Link>
                    </div>
                </div>
            </section>
        </main>
    )
}