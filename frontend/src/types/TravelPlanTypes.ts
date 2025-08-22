export type TravelPlan = {
    id: number
    type: string
    departureLocation: string
    arrivalLocation: string
    departureTime: string
    arrivalTime: string
    notes?: string
    userAdventureId?: number
}

export type CreateTravelPlanPayload = Omit<TravelPlan, "id">
export type UpdateTravelPlanPayload = Partial<CreateTravelPlanPayload>
