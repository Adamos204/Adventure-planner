import { useEffect, useState, type SetStateAction} from "react"
import {Link, useNavigate} from "react-router-dom"
import {listAdventureTemplates} from "../components/TemplatesFetch.tsx"
import type {AdventureTemplate} from "../types/TemplateTypes"

export default function Templates() {
    const [items, setItems] = useState<AdventureTemplate[] | null>(null)
    const [error, setError] = useState<string | null>(null)
    const navigate = useNavigate()

    useEffect(() => {
        listAdventureTemplates()
            .then(setItems)
            .catch((e: { message: SetStateAction<string | null> }) => setError(e instanceof Error ? e.message : "Error"))
    }, [])

    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              Adventure Templates
            </span>
                    </h1>
                    <p className="mt-2 text-gray-600">
                        Not sure what trail to follow? Let our templates guide your steps!
                    </p>
                </div>
            </section>

            <section>
                <div className="max-w-7xl mx-auto px-6 py-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
                    {!items && !error && <div className="text-gray-600">Loading…</div>}
                    {error && <div className="text-red-600">{error}</div>}
                    {items && items.length === 0 && <div className="text-gray-600">No templates yet.</div>}

                    {items && items.map((t) => (
                        <div key={t.id} className="rounded-2xl border bg-white shadow-sm p-5 flex flex-col">
                            <div className="text-lg font-semibold text-gray-900">{t.name}</div>
                            <div className="text-sm text-gray-600 mt-1">{t.location}</div>
                            <p className="text-gray-700 mt-3 line-clamp-4">{t.description}</p>

                            <dl className="grid grid-cols-2 gap-2 text-sm text-gray-700 mt-4">
                                <div><span className="text-gray-500">Days:</span> {t.lengthInDays}</div>
                                <div><span className="text-gray-500">Distance:</span> {t.lengthInKm} km</div>
                                <div className="col-span-2"><span className="text-gray-500">Start:</span> {t.startLocation}</div>
                                <div className="col-span-2"><span className="text-gray-500">End:</span> {t.endLocation}</div>
                            </dl>

                            <div className="mt-5 flex gap-3">
                                <button
                                    onClick={() => navigate("/adventures/new", { state: { template: t } })}
                                    className="rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition"
                                >
                                    Use template
                                </button>
                                <Link
                                    to="/dashboard"
                                    className="rounded-xl border px-4 py-2 text-gray-800 hover:bg-gray-50 transition"
                                >
                                    Cancel
                                </Link>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="max-w-7xl mx-auto px-6 pb-10">
                    <Link to="/dashboard" className="text-blue-600 hover:underline">← Back to Dashboard</Link>
                </div>
            </section>
        </main>
    )
}