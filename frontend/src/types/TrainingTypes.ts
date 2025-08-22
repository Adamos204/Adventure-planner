export type Training = {
    id: number
    date: string
    type: string
    durationInMin: number
    durationInKm: number
    notes?: string
    userId?: number
}

export type CreateTrainingPayload = Omit<Training, "id">
export type UpdateTrainingPayload = Partial<CreateTrainingPayload>