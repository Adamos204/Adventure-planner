import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link, useNavigate } from "react-router-dom";
import type {RegisterPayload} from "../types/RegisterPayload.ts";
import {createUser, login} from "../helper/auth.ts";

const schema = z.object({
    firstname: z.string().min(1, "Firstname is required"),
    surname: z.string().min(1, "Surname is required"),
    username: z.string().min(4, "Username must be at least 4 characters"),
    email: z.string().email("Enter a valid email"),
    password: z.string().min(8, "At least 8 characters"),
    confirmPassword: z.string().min(1, "Please confirm your password"),
}).refine((data) => data.password === data.confirmPassword, {
    message: "Passwords must match",
    path: ["confirmPassword"],
})

type RegisterFormData = z.infer<typeof schema>

export default function Register() {
    const navigate = useNavigate()
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<RegisterFormData>({ resolver: zodResolver(schema) })

    async function onSubmit(data: RegisterFormData) {
        const payload: RegisterPayload = {
            firstname: data.firstname,
            surname: data.surname,
            username: data.username,
            email: data.email,
            password: data.password,
        }

        try {
            await createUser(payload)
            await login(payload.email, payload.password)
            navigate("/dashboard")
        } catch (e) {
            console.error(e)
            alert("Registration failed")
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
              Your journey begins with a single step.
            </span>
                    </h1>
                    <p className="mt-4 text-lg text-gray-600">
                        Create your free account and start planning adventures, gear checklists, and training logs all in one place.
                    </p>
                </div>

                <div className="max-w-md w-full mx-auto rounded-2xl border bg-white p-6 shadow-sm">
                    <h2 className="text-2xl font-bold mb-6 text-gray-900">Register</h2>

                    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                        <div>
                            <label className="text-sm text-gray-700">Firstname</label>
                            <input {...register("firstname")} placeholder="Adam"
                                   className={`${inputBase} mt-1 ${errors.firstname ? "border-red-500" : "border-gray-300"}`} />
                            {errors.firstname && <p className={errorClass}>{errors.firstname.message}</p>}
                        </div>

                        <div>
                            <label className="text-sm text-gray-700">Surname</label>
                            <input {...register("surname")} placeholder="Vesely"
                                   className={`${inputBase} mt-1 ${errors.surname ? "border-red-500" : "border-gray-300"}`} />
                            {errors.surname && <p className={errorClass}>{errors.surname.message}</p>}
                        </div>

                        <div>
                            <label className="text-sm text-gray-700">Username</label>
                            <input {...register("username")} placeholder="adamos"
                                   className={`${inputBase} mt-1 ${errors.username ? "border-red-500" : "border-gray-300"}`} />
                            {errors.username && <p className={errorClass}>{errors.username.message}</p>}
                        </div>

                        <div>
                            <label className="text-sm text-gray-700">Email</label>
                            <input {...register("email")} type="email" placeholder="adam@example.com"
                                   className={`${inputBase} mt-1 ${errors.email ? "border-red-500" : "border-gray-300"}`} />
                            {errors.email && <p className={errorClass}>{errors.email.message}</p>}
                        </div>

                        <div>
                            <label className="text-sm text-gray-700">Password</label>
                            <input {...register("password")} type="password" placeholder="••••••••"
                                   className={`${inputBase} mt-1 ${errors.password ? "border-red-500" : "border-gray-300"}`} />
                            {errors.password && <p className={errorClass}>{errors.password.message}</p>}
                        </div>

                        <div>
                            <label className="text-sm text-gray-700">Confirm Password</label>
                            <input {...register("confirmPassword")} type="password" placeholder="••••••••"
                                   className={`${inputBase} mt-1 ${errors.confirmPassword ? "border-red-500" : "border-gray-300"}`} />
                            {errors.confirmPassword && <p className={errorClass}>{errors.confirmPassword.message}</p>}
                        </div>

                        <button
                            type="submit"
                            disabled={isSubmitting}
                            className="w-full rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition disabled:opacity-60"
                        >
                            Create account
                        </button>
                    </form>

                    <p className="mt-4 text-sm text-gray-600 text-center">
                        Already have an account?{" "}
                        <Link to="/login" className="text-blue-600 hover:underline">Log in</Link>
                    </p>
                </div>
            </div>
        </main>
    );
}