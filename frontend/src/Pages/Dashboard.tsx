import { Link } from "react-router-dom"

type Training = { id: string; date: string; title: string; duration: string }
type Adventure = { id: string; date: string; name: string; status?: "Planned" | "Booked" | "Prep" }

const userName = localStorage.getItem("userName") || "Adamos"

const recentTrainings: Training[] = [
    { id: "t1", date: "2025-08-18", title: "Gym – Push Day", duration: "60 min" },
    { id: "t2", date: "2025-08-17", title: "Krav Maga Session", duration: "90 min" },
    { id: "t3", date: "2025-08-15", title: "Hike – Local Trail", duration: "120 min" },
]

const upcomingAdventures: Adventure[] = [
    { id: "a1", date: "2025-09-10", name: "Ben Nevis", status: "Planned" },
    { id: "a2", date: "2025-10-03", name: "Kilimanjaro Prep Hike", status: "Prep" },
]

const formatDate = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function Dashboard() {
    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8">
                    <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              Welcome back, {userName}.
            </span>
                    </h1>
                    <p className="mt-2 text-gray-600">Here’s a quick look at your training and upcoming adventures.</p>
                </div>
            </section>

            <section>
                <div className="max-w-7xl mx-auto px-6 py-8 grid gap-8 md:grid-cols-2">
                    {/* Trainings */}
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <h2 className="text-lg font-semibold text-gray-900">Recent Trainings</h2>
                            <div className="flex items-center gap-3">
                                <Link to="/training" className="text-sm text-gray-700 hover:underline">View all</Link>
                                <Link
                                    to="/training/new"
                                    className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                                >
                                    Add
                                </Link>
                            </div>
                        </div>
                        <ul className="divide-y">
                            {recentTrainings.length === 0 ? (
                                <li className="px-6 py-6 text-gray-600">No training yet. Add your first session.</li>
                            ) : (
                                recentTrainings.map((t) => (
                                    <li key={t.id} className="px-6 py-4 flex items-center justify-between">
                                        <div>
                                            <div className="font-medium text-gray-900">{t.title}</div>
                                            <div className="text-sm text-gray-600">{formatDate(t.date)} • {t.duration}</div>
                                        </div>
                                        <Link to={`/training/${t.id}`} className="text-blue-600 text-sm hover:underline">
                                            Details
                                        </Link>
                                    </li>
                                ))
                            )}
                        </ul>
                    </div>

                    {/* Adventures */}
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <h2 className="text-lg font-semibold text-gray-900">Upcoming Adventures</h2>
                            <div className="flex items-center gap-3">
                                <Link to="/adventures" className="text-sm text-gray-700 hover:underline">View all</Link>
                                <Link
                                    to="/adventures/new"
                                    className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                                >
                                    Add
                                </Link>
                            </div>
                        </div>
                        <ul className="divide-y">
                            {upcomingAdventures.length === 0 ? (
                                <li className="px-6 py-6 text-gray-600">No adventures planned. Add your first one.</li>
                            ) : (
                                upcomingAdventures.map((a) => (
                                    <li key={a.id} className="px-6 py-4 flex items-center justify-between">
                                        <div>
                                            <div className="font-medium text-gray-900">{a.name}</div>
                                            <div className="text-sm text-gray-600">
                                                {formatDate(a.date)}{a.status ? ` • ${a.status}` : ""}
                                            </div>
                                        </div>
                                        <Link to={`/adventures/${a.id}`} className="text-blue-600 text-sm hover:underline">
                                            Details
                                        </Link>
                                    </li>
                                ))
                            )}
                        </ul>
                    </div>
                </div>
            </section>
        </main>
    )
}