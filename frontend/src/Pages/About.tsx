import { Link } from "react-router-dom"

const YOUR_NAME = "Adamos"
const YOUR_ROLE = "Full-stack Developer • Mountaineer"
const YOUR_AVATAR_URL = "" // e.g. "https://example.com/me.jpg"

const getInitial = (name: string) => (name?.trim()?.[0] ?? "?").toUpperCase()

export default function About() {
    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-12">
                    <div className="max-w-3xl">
                        <h1 className="text-4xl font-extrabold tracking-tight">
              <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
                Adventure Planner
              </span>
                        </h1>
                        <p className="mt-4 text-lg text-gray-600">
                            Plan hikes and climbs you’ll actually finish. Adventure Planner keeps your routes,
                            gear lists, and training logs in one place—so you can focus on the summit.
                        </p>

                        <div className="mt-6 flex gap-3">
                            <Link
                                to="/register"
                                className="inline-flex items-center rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition"
                            >
                                Create an account
                            </Link>
                            <Link
                                to="/dashboard"
                                className="inline-flex items-center rounded-xl border px-4 py-2 text-gray-800 hover:bg-gray-50 transition"
                            >
                                Go to dashboard
                            </Link>
                        </div>
                    </div>
                </div>
            </section>
            <section>
                <div className="max-w-7xl mx-auto px-6 py-12">
                    <h2 className="text-2xl font-semibold text-gray-900">What you can do</h2>

                    <div className="mt-6 grid gap-6 md:grid-cols-3">
                        <div className="rounded-2xl border bg-white p-6 shadow-sm">
                            <h3 className="font-semibold text-gray-900">Plan Your Adventures</h3>
                            <p className="mt-2 text-gray-600">
                                Create custom hikes or pick from templates like <em>Kilimanjaro</em> or <em>Ben Nevis</em>.
                            </p>
                        </div>

                        <div className="rounded-2xl border bg-white p-6 shadow-sm">
                            <h3 className="font-semibold text-gray-900">Gear Checklists</h3>
                            <p className="mt-2 text-gray-600">
                                Build per-adventure gear lists, mark items packed, and reuse your favorites.
                            </p>
                        </div>

                        <div className="rounded-2xl border bg-white p-6 shadow-sm">
                            <h3 className="font-semibold text-gray-900">Training Log</h3>
                            <p className="mt-2 text-gray-600">
                                Track gym days and Krav Maga sessions. See weekly totals and keep your prep consistent.
                            </p>
                        </div>
                    </div>
                </div>
            </section>
            <section className="min-h-[60vh] flex items-center justify-center border-t">
                <div className="max-w-4xl mx-auto flex flex-col md:flex-row items-center justify-between gap-8 px-6">
                    <h2 className="text-3xl md:text-4xl font-extrabold tracking-tight text-center md:text-left">
                        <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
                            Ready to prep your first summit?
                        </span>
                    </h2>
                    <div className="flex gap-4">
                        <Link to={"/register"} className="px-6 py-3 rounded-xl bg-blue-600 text-white font-medium shadow hover:opacity-90 transition">
                            Get started
                        </Link>
                        <Link to={"/dashboard"} className="px-6 py-3 rounded-xl border border-gray-300 font-medium hover:bg-gray-50 transition">
                            View dashboard
                        </Link>
                    </div>
                </div>
            </section>
            <section className="bg-gray-50 border-t">
                <div className="max-w-7xl mx-auto px-6 py-12">
                    <h2 className="text-2xl font-semibold text-gray-900">Designer</h2>
                    <p className="mt-2 text-gray-600">Built and maintained by a solo developer.</p>

                    <div className="mt-8 grid gap-6 sm:grid-cols-2 md:grid-cols-3">
                        <div className="rounded-2xl border bg-white p-6 shadow-sm text-center sm:col-span-1">
                            {YOUR_AVATAR_URL ? (
                                <img
                                    src={YOUR_AVATAR_URL}
                                    alt={`${YOUR_NAME} avatar`}
                                    className="mx-auto h-20 w-20 rounded-full object-cover ring-4 ring-gray-100"
                                    loading="lazy"
                                />
                            ) : (
                                <div className="mx-auto h-20 w-20 rounded-full bg-gradient-to-r from-blue-600 to-purple-500 flex items-center justify-center text-white font-bold text-2xl ring-4 ring-gray-100">
                                    {getInitial(YOUR_NAME)}
                                </div>
                            )}

                            <h3 className="mt-4 font-semibold text-gray-900">{YOUR_NAME}</h3>
                            <p className="text-gray-600 text-sm">{YOUR_ROLE}</p>

                            {/* For later/}
                            {/* <div className="mt-4 flex justify-center gap-3 text-sm">
                <a href="https://github.com/your-handle" className="text-blue-600 hover:underline" target="_blank">GitHub</a>
                <span className="text-gray-300">•</span>
                <a href="https://www.linkedin.com/in/your-handle" className="text-blue-600 hover:underline" target="_blank">LinkedIn</a>
              </div> */}
                        </div>
                    </div>
                </div>
            </section>
            <section>
                <div className="max-w-7xl mx-auto px-6 py-12">
                    <h2 className="text-2xl font-semibold text-gray-900">Roadmap</h2>
                    <p className="mt-2 text-gray-600">What’s live and what’s next:</p>

                    <ul className="mt-6 space-y-4">
                        <li className="flex items-start gap-3">
                            <span className="h-6 w-6 flex items-center justify-center rounded-full bg-blue-600 text-white text-sm">✓</span>
                            <span className="text-gray-700">Core features: login, adventures, gear, training log</span>
                        </li>
                        <li className="flex items-start gap-3">
                            <span className="h-6 w-6 flex items-center justify-center rounded-full bg-blue-600 text-white text-sm">•</span>
                            <span className="text-gray-700">Weather API integration</span>
                        </li>
                        <li className="flex items-start gap-3">
                            <span className="h-6 w-6 flex items-center justify-center rounded-full bg-blue-600 text-white text-sm">•</span>
                            <span className="text-gray-700">Export to PDF/Excel</span>
                        </li>
                        <li className="flex items-start gap-3">
                            <span className="h-6 w-6 flex items-center justify-center rounded-full bg-blue-600 text-white text-sm">✓</span>
                            <span className="text-gray-700">Personal dashboard with stats</span>
                        </li>
                        <li className="flex items-start gap-3">
                            <span className="h-6 w-6 flex items-center justify-center rounded-full bg-blue-600 text-white text-sm">•</span>
                            <span className="text-gray-700">Pre-made adventure templates</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>
    )
}