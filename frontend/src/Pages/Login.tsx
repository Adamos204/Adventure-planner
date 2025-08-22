import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import {Link, useLocation, useNavigate} from "react-router-dom"
import {fetchMe, login} from "../helper/auth.ts";

const schema = z.object({
    email: z.string().email("Enter a valid email"),
    password: z.string().min(8, "At least 8 characters"),
})
type LoginFormData = z.infer<typeof schema>

interface LocationState {
    from?: {
        pathname: string
    }
}

export default function Login() {
    const navigate = useNavigate()
    const location = useLocation()
    const from = (location.state as LocationState)?.from?.pathname || "/dashboard"
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<LoginFormData>({ resolver: zodResolver(schema) })

    async function onSubmit(data: LoginFormData) {
        try {
            await login(data.email, data.password)
            const me = await fetchMe()
            if (me) {
                localStorage.setItem("userName", me.firstname)
            }
            navigate(from, {replace: true})
        } catch (err) {
            console.error(err)
            alert("Login failed")
        }
    }

    const inputBase =
        "w-full rounded-xl border px-3 py-2 outline-none transition focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
    const errorClass = "text-xs text-red-600"

    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white flex items-center">
            <div className="max-w-7xl mx-auto w-full px-6 py-12 grid gap-12 md:grid-cols-2">
                <div className="flex flex-col justify-center">
                    <h1 className="text-4xl sm:text-5xl font-extrabold tracking-tight">
            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
              Welcome back.
            </span>
                    </h1>
                    <p className="mt-4 text-lg text-gray-600">
                        Pick up where you left off—access your adventures, gear, and training in one place.
                    </p>
                </div>

                <div className="max-w-md w-full mx-auto rounded-2xl border bg-white p-6 shadow-sm">
                    <h2 className="text-2xl font-bold mb-6 text-gray-900">Login</h2>

                    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                        <div>
                            <label className="text-sm text-gray-700">Email</label>
                            <input
                                {...register("email")}
                                type="email"
                                placeholder="jane@example.com"
                                className={`${inputBase} mt-1 ${errors.email ? "border-red-500" : "border-gray-300"}`}
                            />
                            {errors.email && <p className={errorClass}>{errors.email.message}</p>}
                        </div>

                        <div>
                            <label className="text-sm text-gray-700">Password</label>
                            <input
                                {...register("password")}
                                type="password"
                                placeholder="••••••••"
                                className={`${inputBase} mt-1 ${errors.password ? "border-red-500" : "border-gray-300"}`}
                            />
                            {errors.password && <p className={errorClass}>{errors.password.message}</p>}
                        </div>

                        <button
                            type="submit"
                            disabled={isSubmitting}
                            className="w-full rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition disabled:opacity-60"
                        >
                            Log in
                        </button>
                    </form>

                    <p className="mt-4 text-sm text-gray-600 text-center">
                        Don’t have an account?{" "}
                        <Link to="/register" className="text-blue-600 hover:underline">
                            Register
                        </Link>
                    </p>
                </div>
            </div>
        </main>
    )
}