import { Link } from "react-router-dom"

type Training = { id: string; date: string; title: string; duration: string }

const trainings: Training[] = [
    { id: "t1", date: "2025-08-18", title: "Gym – Push Day", duration: "60 min" },
    { id: "t2", date: "2025-08-17", title: "Krav Maga Session", duration: "90 min" },
    { id: "t3", date: "2025-08-15", title: "Hike – Local Trail", duration: "120 min" },
]

const formatDate = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function TrainingsList() {
    const items = [...trainings].sort((a, b) => +new Date(b.date) - +new Date(a.date))
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
                            <Link
                                to="/training/new"
                                className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                            >
                                Add
                            </Link>
                        </div>

                        <ul className="divide-y">
                            {items.length === 0 ? (
                                <li className="px-6 py-6 text-gray-600">No trainings yet.</li>
                            ) : (
                                items.map((t) => (
                                    <li key={t.id} className="px-6 py-4 flex items-center justify-between">
                                        <div>
                                            <div className="font-medium text-gray-900">{t.title}</div>
                                            <div className="text-sm text-gray-600">
                                                {formatDate(t.date)} • {t.duration}
                                            </div>
                                        </div>
                                        <div className="flex items-center gap-4">
                                            <Link to={`/training/${t.id}`} className="text-blue-600 text-sm hover:underline">
                                                Details
                                            </Link>
                                            <Link to={`/training/${t.id}/edit`} className="text-gray-700 text-sm hover:underline">
                                                Edit
                                            </Link>
                                        </div>
                                    </li>
                                ))
                            )}
                        </ul>
                    </div>

                    <div className="mt-6">
                        <Link to="/dashboard" className="text-blue-600 hover:underline">
                            ← Back to Dashboard
                        </Link>
                    </div>
                </div>
            </section>
        </main>
    )
}