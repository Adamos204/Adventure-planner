import { Link } from "react-router-dom"

type Adventure = { id: string; date: string; name: string; status?: "Planned" | "Booked" | "Prep" }

const adventures: Adventure[] = [
    { id: "a1", date: "2025-09-10", name: "Ben Nevis", status: "Planned" },
    { id: "a2", date: "2025-10-03", name: "Kilimanjaro Prep Hike", status: "Prep" },
]

const formatDate = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function AdventuresList() {
    const items = [...adventures].sort((a, b) => +new Date(a.date) - +new Date(b.date))
    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              Upcoming Adventures
            </span>
                    </h1>
                    <p className="mt-2 text-gray-600">Your planned and in-prep routes.</p>
                </div>
            </section>

            <section>
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <div className="text-lg font-semibold text-gray-900">Adventures</div>
                            <Link
                                to="/adventures/new"
                                className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                            >
                                Add
                            </Link>
                        </div>

                        <ul className="divide-y">
                            {items.length === 0 ? (
                                <li className="px-6 py-6 text-gray-600">No adventures yet.</li>
                            ) : (
                                items.map((a) => (
                                    <li key={a.id} className="px-6 py-4 flex items-center justify-between">
                                        <div>
                                            <div className="font-medium text-gray-900">{a.name}</div>
                                            <div className="text-sm text-gray-600">
                                                {formatDate(a.date)}{a.status ? ` • ${a.status}` : ""}
                                            </div>
                                        </div>
                                        <div className="flex items-center gap-4">
                                            <Link to={`/adventures/${a.id}`} className="text-blue-600 text-sm hover:underline">
                                                Details
                                            </Link>
                                            <Link to={`/adventures/${a.id}/edit`} className="text-gray-700 text-sm hover:underline">
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