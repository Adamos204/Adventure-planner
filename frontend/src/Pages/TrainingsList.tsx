import { useEffect, useState } from "react"
import { Link } from "react-router-dom";
import { fetchMe } from "../helper/auth.ts";
import type { Training } from "../types/TrainingTypes"
import {listTrainings} from "../components/TrainingsFetch.tsx";

const fmt = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function TrainingsList() {
    const [items, setItems] = useState<Training[] | null>(null)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        async function load() {
            try {
                const me = await fetchMe()
                if (!me) throw new Error("Not authenticated")
                const data = await listTrainings(me.id)
                const sorted = [...data].sort((a, b) => +new Date(b.date) - +new Date(a.date))
                setItems(sorted)
            } catch (e) {
                setError(e instanceof Error ? e.message : "Error")
            }
        }
        load()
    }, [])

    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              All Trainings
            </span>
                    </h1>
                    <p className="mt-2 text-gray-600">Your recent and past training sessions.</p>
                </div>
            </section>

            <section>
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <div className="text-lg font-semibold text-gray-900">Trainings</div>
                            <Link to="/trainings/new" className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition">
                                Add
                            </Link>
                        </div>

                        {!items && !error && <div className="px-6 py-6 text-gray-600">Loading…</div>}
                        {error && <div className="px-6 py-6 text-red-600">{error}</div>}

                        {items && (
                            <ul className="divide-y">
                                {items.length === 0 ? (
                                    <li className="px-6 py-6 text-gray-600">No trainings yet.</li>
                                ) : (
                                    items.map((t) => (
                                        <li key={t.id} className="px-6 py-4 flex items-center justify-between">
                                            <div>
                                                <div className="font-medium text-gray-900">{t.type}</div>
                                                <div className="text-sm text-gray-600">{fmt(t.date)} • {t.durationInMin} min • {t.durationInKm} km</div>
                                            </div>
                                            <div className="flex items-center gap-4">
                                                <Link to={`/trainings/${t.id}`} className="text-blue-600 text-sm hover:underline">Details</Link>
                                                <Link to={`/trainings/${t.id}/edit`} className="text-gray-700 text-sm hover:underline">Edit</Link>
                                            </div>
                                        </li>
                                    ))
                                )}
                            </ul>
                        )}
                    </div>

                    <div className="mt-6">
                        <Link to="/dashboard" className="text-blue-600 hover:underline">← Back to Dashboard</Link>
                    </div>
                </div>
            </section>
        </main>
    )
}
