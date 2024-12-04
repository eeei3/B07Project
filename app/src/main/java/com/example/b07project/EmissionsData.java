package com.example.b07project;

/**
 * EmissionsData class containing fields relating to calculating emissions
 */
public class EmissionsData {

    //Transportation Data
    final public static double[][] carEmission = {
            //gas, diesel, hybrid, electric, not sure
            // "Up to 5,000 km"
            {0.24 * 5000, 0.27 * 5000, 0.16 * 5000, 0.05 * 5000, 0.18 * 5000},
            // "5,000–10,000 km"
            {0.24 * 10000, 0.27 * 10000, 0.16 * 10000, 0.05 * 10000, 0.18 * 10000},
            // "10,000–15,000 km"
            {0.24 * 15000, 0.27 * 15000, 0.16 * 15000, 0.05 * 15000, 0.18 * 15000},
            // "15,000–20,000 km"
            {0.24 * 20000, 0.27 * 20000, 0.16 * 20000, 0.05 * 20000, 0.18 * 20000},
            // "20,000–25,000 km"
            {0.24 * 25000, 0.27 * 25000, 0.16 * 25000, 0.05 * 25000, 0.18 * 25000},
            // "More than 25,000 km"
            {0.24 * 35000, 0.27 * 35000, 0.16 * 35000, 0.05 * 35000, 0.18 * 35000}
    };
    final public static double[][] publictransportEmission = {
            //never, occasionally, frequently, always
            {0, 246, 573, 573}, //under 1 hour
            {0, 819, 1911, 1911}, //1-3 hours
            {0, 1638, 3822, 3822}, //3-5 hours
            {0, 3071, 7166, 7166}, //5-10 hours
            {0, 4095, 9555, 9555}  //more than 10 hours
    };
    final public static double[] shortflightEmission = {0, 225, 600, 1200, 1800};
    final public static double[] longflightEmission = {0, 825, 2200, 4400, 6600};

    //Food Data
    final public static double[][][] foodEmission = {
            //vegetarian
            {{1000}},
            //vegan
            {{500}},
            //pescatarian
            {{1500}},
            //meat-based
            {
                    //daily, frequently, occasionally, never
                    {2500, 1900, 1300, 0}, //beef
                    {1450, 860, 450, 0},  //pork
                    {950, 600, 200, 0},  //chicken
                    {800, 500, 150, 0},  //fish
            }
    };
    final public static double[] foodwasteEmission = {0, 23.4, 70.2, 140.4};

    //Housing Data
    final public static double[][][][][] housingEmission = {
            //Detached house
            {
                    //Under 1000 square feet
                    {
                            //Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood, other
                                    {2400, 200, 2100, 2870, 2170, 2170},  // 1 occupant
                                    {2600, 250, 2650, 3470, 2370, 2370},  // 2 occupants
                                    {2700, 380, 3200, 4370, 2670, 2670},  // 3-4 occupants
                                    {3000, 450, 3700, 5270, 2970, 2970}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood, other
                                    {2440, 400, 5200, 4400, 2340, 2340},  // 1 occupant
                                    {2640, 500, 5400, 4600, 2540, 2540},  // 2 occupants
                                    {2940, 550, 5700, 4900, 2840, 2840},  // 3-4 occupants
                                    {3240, 600, 6000, 5200, 3140, 3140}   // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood, other
                                    {2610, 1200, 6100, 5400, 2510, 2510},  // 1 occupant
                                    {2810, 1450, 6250, 5600, 2710, 2710},  // 2 occupants
                                    {3110, 1600, 6700, 5900, 3010, 3010},  // 3-4 occupants
                                    {3410, 1800, 6950, 6200, 3310, 3310}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2780, 1700, 7200, 6400, 2680, 2680},  // 1 occupant
                                    {2980, 1900, 7400, 6600, 2880, 2880},  // 2 occupants
                                    {3280, 2050, 7700, 6900, 3180, 3180},  // 3-4 occupants
                                    {3580, 2200, 8000, 7200, 3480, 3480}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3100, 2300, 8200, 7400, 3000, 3000},  // 1 occupant
                                    {3100, 2500, 8400, 7600, 3200, 3200},  // 2 occupants
                                    {3600, 2700, 8700, 7900, 3500, 3500},  // 3-4 occupants
                                    {3900, 3000, 9000, 8200, 3800, 3800}   // 4+ occupants
                            }
                    },
                    // 1000-2000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2800, 300, 3800, 3770, 3670, 3670},  // 1 occupant
                                    {3000, 380, 4000, 4470, 4170, 4170},  // 2 occupants
                                    {3380, 500, 4700, 5670, 4870, 4870},  // 3-4 occupants
                                    {3860, 600, 5350, 6570, 5670, 5670}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {5900, 600, 5300, 3940, 3840, 3840},  // 1 occupant
                                    {6100, 800, 5440, 4640, 4340, 4340},  // 2 occupants
                                    {6400, 1050, 5800, 5740, 5040, 5040}, // 3-4 occupants
                                    {6700, 1200, 6100, 6740, 5840, 5840}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {6500, 1200, 6200, 7100, 4010, 4010},  // 1 occupant
                                    {6700, 1450, 6400, 7230, 4510, 4510},  // 2 occupants
                                    {7000, 1600, 6700, 7400, 5210, 5210},  // 3-4 occupants
                                    {7300, 1800, 7000, 7550, 6010, 6010}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {7100, 1800, 7200, 4280, 4180, 4180},  // 1 occupant
                                    {7300, 2000, 7400, 4980, 4680, 4680},  // 2 occupants
                                    {7600, 2250, 7700, 5985, 5380, 5380},  // 3-4 occupants
                                    {7900, 2400, 8000, 7080, 6180, 6180}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {8300, 2400, 8200, 4600, 4500, 4500},  // 1 occupant
                                    {8500, 2600, 8400, 5300, 5000, 5000},  // 2 occupants
                                    {8800, 2800, 8700, 6350, 5750, 5750},  // 3-4 occupants
                                    {9100, 3100, 9000, 7400, 6500, 6500}   // 4+ occupants
                            }
                    },
                    // 2000+ square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2800, 320, 5400, 5570, 4170, 4170},  // 1 occupant
                                    {2880, 450, 6200, 6170, 4670, 4670}, // 2 occupants
                                    {3000, 520, 7000, 6970, 5270, 5270}, // 3-4 occupants
                                    {3230, 675, 8900, 7970, 6170, 6170}  // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3000, 600, 10500, 5740, 4340, 4340},  // 1 occupant
                                    {3200, 900, 11000, 6340, 4840, 4840}, // 2 occupants
                                    {3500, 1500, 12500, 7240, 5640, 5640}, // 3-4 occupants
                                    {3800, 1800, 14000, 8140, 6340, 6340}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3200, 1800, 14000, 6000, 4510, 4510},  // 1 occupant
                                    {3400, 2100, 15500, 6500, 5010, 5010}, // 2 occupants
                                    {3700, 2300, 16250, 7000, 5710, 5710}, // 3-4 occupants
                                    {4000, 2700, 17500, 7500, 6510, 6510}  // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3400, 2700, 17500, 6600, 4680, 4680},  // 1 occupant
                                    {3600, 3100, 18100, 7150, 5180, 5180}, // 2 occupants
                                    {4100, 3400, 20000, 7700, 5980, 5980}, // 3-4 occupants
                                    {4400, 3600, 21000, 8250, 6680, 6680}  // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3600, 3600, 21000, 6900, 5000, 5000},  // 1 occupant
                                    {3800, 3800, 22000, 7700, 5500, 5500},  // 2 occupants
                                    {4100, 4000, 23500, 8400, 6250, 6250},  // 3-4 occupants
                                    {4400, 4200, 25000, 9000, 7000, 7000}   // 4+ occupants
                            }
                    }
            },
            // Semi-Detached house
            {
                    // Under 1000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2160, 300, 2500, 2200, 2100, 2100},  // 1 occupant
                                    {2349, 410, 2592, 2300, 2450, 2450},  // 2 occupants
                                    {2732, 500, 2680, 2450, 2700, 2700},  // 3-4 occupants
                                    {3199, 580, 2750, 2600, 3000, 3000}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2400, 410, 2800, 2600, 3000, 3000},  // 1 occupant
                                    {2600, 500, 3000, 2800, 3200, 3200},  // 2 occupants
                                    {2900, 560, 3300, 3100, 3500, 3500},  // 3-4 occupants
                                    {3200, 600, 3600, 3400, 3800, 3800}   // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2600, 1210, 3000, 2800, 3200, 3200},  // 1 occupant
                                    {2800, 1450, 3200, 3000, 3400, 3400},  // 2 occupants
                                    {3100, 1620, 3500, 3300, 3700, 3700},  // 3-4 occupants
                                    {3400, 1820, 3800, 3600, 4000, 4000}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2800, 1700, 3200, 3000, 3400, 3400},  // 1 occupant
                                    {3000, 1900, 3400, 3200, 3600, 3600},  // 2 occupants
                                    {3300, 2000, 3700, 3500, 3900, 3900},  // 3-4 occupants
                                    {3600, 2200, 4000, 3800, 4200, 4200}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3000, 2200, 3400, 3200, 3600, 3600},  // 1 occupant
                                    {3200, 2500, 3600, 3400, 3800, 3800},  // 2 occupants
                                    {3500, 2600, 3900, 3700, 4100, 4100},  // 3-4 occupants
                                    {3800, 3000, 4200, 4000, 4400, 4400}   // 4+ occupants
                            }
                    },
                    // 1000-2000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2443, 300, 3400, 4000, 1500, 1500},  // 1 occupant
                                    {2727, 410, 3499, 4300, 1800, 1800},  // 2 occupants
                                    {3151, 550, 3599, 4700, 2100, 2100},  // 3-4 occupants
                                    {3578, 605, 3700, 4900, 2500, 2500}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4000, 600, 4000, 5000, 2500, 2500},  // 1 occupant
                                    {5200, 800, 4600, 6200, 2700, 2700},  // 2 occupants
                                    {6800, 1050, 5100, 7300, 3500, 3500}, // 3-4 occupants
                                    {7500, 1200, 6000, 8000, 4000, 4000}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4500, 1200, 6100, 7000, 4100, 4100},  // 1 occupant
                                    {6000, 1550, 6900, 8000, 4300, 4300},  // 2 occupants
                                    {7800, 1700, 7300, 9100, 4850, 4850},  // 3-4 occupants
                                    {8500, 1800, 8500, 10000, 5500, 5500}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {5000, 1800, 8000, 9000, 5500, 5500},  // 1 occupant
                                    {6500, 2000, 8800, 10200, 6000, 6000},  // 2 occupants
                                    {8800, 2250, 9200, 11000, 6800, 6800},  // 3-4 occupants
                                    {10000, 2400, 10500, 12000, 7100, 7100}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {6000, 2400, 10550, 12000, 7220, 7220},  // 1 occupant
                                    {7800, 2500, 10900, 13200, 8000, 8000},  // 2 occupants
                                    {9800, 2800, 11200, 14100, 8600, 8600},  // 3-4 occupants
                                    {11000, 3200, 12000, 15000, 9100, 9100}   // 4+ occupants
                            }
                    },
                    // 2000+ square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2821, 300, 3820, 4370, 3970, 3970},  // 1 occupant
                                    {3010, 560, 4000, 4870, 4470, 4470}, // 2 occupants
                                    {3261, 890, 4307, 5670, 5270, 5270}, // 3-4 occupants
                                    {3578, 1000, 4400, 6370, 5970, 5970}  // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {7500, 1200, 5500, 4540, 4140, 4140},  // 1 occupant
                                    {8800, 1400, 6000, 5040, 4640, 4640}, // 2 occupants
                                    {10800, 1650, 7200, 5840, 5340, 5340}, // 3-4 occupants
                                    {12500, 1800, 8500, 6540, 6140, 6140}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {10000, 1800, 8500, 4710, 4310, 4310},  // 1 occupant
                                    {12000, 2000, 9200, 5210, 4810, 4810}, // 2 occupants
                                    {14000, 2300, 10200, 6010, 5610, 5610}, // 3-4 occupants
                                    {15000, 2400, 11000, 6710, 6310, 6310}  // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    // x-axis: type of fuel, y-axis: number of occupants
                                    {12500, 2400, 11000, 4880, 4480, 4480},  // 1 occupant
                                    {14200, 2600, 12000, 5380, 4980, 4980}, // 2 occupants
                                    {16000, 2820, 13500, 6180, 5780, 5780}, // 3-4 occupants
                                    {17500, 3000, 14000, 6880, 6480, 6480}  // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {15000, 3000, 14000, 5200, 4800, 4800},  // 1 occupant
                                    {16800, 3500, 14800, 5700, 5300, 5300},  // 2 occupants
                                    {18200, 4000, 15500, 6500, 6150, 6150},  // 3-4 occupants
                                    {19000, 4500, 16000, 7200, 6800, 6800}   // 4+ occupants
                            }
                    }
            },
            // Townhouse
            {
                    // Under 1000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {1971, 300, 2400, 1500, 2000, 2000},  // 1 occupant
                                    {2160, 410, 2523, 1850, 2250, 2250},  // 2 occupants
                                    {2500, 500, 2600, 2100, 2500, 2500},  // 3-4 occupants
                                    {2800, 550, 2720, 2500, 2600, 2600}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2800, 500, 2800, 2500, 2800, 2800},  // 1 occupant
                                    {2910, 550, 3100, 2800, 3000, 3000},  // 2 occupants
                                    {3000, 580, 3250, 3400, 3300, 3300},  // 3-4 occupants
                                    {3200, 600, 3500, 3800, 3400, 3400}   // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3000, 1000, 3600, 3000, 3000, 3000},  // 1 occupant
                                    {3210, 1250, 3740, 3500, 3300, 3300},  // 2 occupants
                                    {3500, 1320, 3900, 4100, 3520, 3520},  // 3-4 occupants
                                    {3800, 1420, 4050, 5000, 3800, 3800}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4000, 1600, 4500, 3700, 3330, 3330},  // 1 occupant
                                    {5500, 1750, 4600, 4500, 3500, 3500},  // 2 occupants
                                    {6200, 1900, 4800, 5200, 3720, 3720},  // 3-4 occupants
                                    {8000, 2000, 5100, 5800, 4000, 4000}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {8000, 2000, 5000, 5800, 3500, 3500},  // 1 occupant
                                    {9500, 2100, 5200, 6800, 3700, 3700},  // 2 occupants
                                    {10200, 2300, 5300, 7200, 4000, 4000},  // 3-4 occupants
                                    {12000, 3000, 5600, 7800, 4300, 4300}   // 4+ occupants
                            }
                    },
                    // 1000-2000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2443, 300, 2590, 3170, 1400, 1400},  // 1 occupant
                                    {2750, 380, 2620, 3770, 1560, 1560},  // 2 occupants
                                    {3111, 500, 2730, 4670, 1900, 1900},  // 3-4 occupants
                                    {3580, 590, 2800, 5570, 2200, 2200}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4000, 550, 3800, 5600, 2400, 2400},  // 1 occupant
                                    {5000, 700, 4320, 5940, 2600, 2600},  // 2 occupants
                                    {6500, 950, 4800, 6140, 3300, 3300}, // 3-4 occupants
                                    {7300, 1100, 5500, 6340, 3800, 3800}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4300, 1200, 5000, 6000, 4000, 4000},  // 1 occupant
                                    {5500, 1350, 5800, 6200, 4310, 4310},  // 2 occupants
                                    {6800, 1520, 6200, 6420, 4600, 4600},  // 3-4 occupants
                                    {8340, 1700, 6100, 6500, 5100, 5100}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4800, 1700, 5350, 3680, 3800, 3800},  // 1 occupant
                                    {6300, 1900, 5500, 4280, 3800, 3800},  // 2 occupants
                                    {8500, 2150, 5720, 5180, 4220, 4220},  // 3-4 occupants
                                    {9000, 2400, 5900, 6080, 4400, 4400}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {9500, 2500, 5370, 6000, 4000, 4000},  // 1 occupant
                                    {10100, 2780, 5500, 6600, 4640, 4640},  // 2 occupants
                                    {11200, 3000, 5800, 6800, 5000, 5000},  // 3-4 occupants
                                    {14000, 3500, 6200, 7400, 5430, 5430}   // 4+ occupants
                            }
                    },
                    // 2000+ square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2822, 300, 2810, 3340, 3800, 3800},  // 1 occupant
                                    {3010, 560, 3000, 3940, 4070, 4070}, // 2 occupants
                                    {3300, 890, 3468, 4840, 5000, 5000}, // 3-4 occupants
                                    {3600, 1000, 3760, 5740, 5600, 5600}  // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3600, 1200, 4300, 5900, 3500, 3500},  // 1 occupant
                                    {3840, 1380, 4900, 6330, 3930, 3930}, // 2 occupants
                                    {3900, 1600, 5320, 6440, 4360, 4360}, // 3-4 occupants
                                    {5100, 1750, 5800, 6900, 5000, 5000}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {5000, 1800, 5300, 3510, 4100, 4100},  // 1 occupant
                                    {6200, 2000, 5690, 4110, 4500, 4500}, // 2 occupants
                                    {7000, 2200, 6250, 5010, 4780, 4780}, // 3-4 occupants
                                    {8000, 2300, 6500, 5910, 5360, 5360}  // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {8000, 2400, 5440, 3800, 4200, 4200},  // 1 occupant
                                    {8300, 2500, 5600, 4500, 4640, 4640}, // 2 occupants
                                    {9000, 2650, 5800, 5380, 5000, 5000}, // 3-4 occupants
                                    {9500, 2800, 6000, 6200, 5400, 5400}  // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {9500, 2300, 5670, 6200, 4300, 4300},  // 1 occupant
                                    {1010, 3000, 5800, 6900, 4700, 4700},  // 2 occupants
                                    {10300, 3800, 6100, 7500, 5100, 5100},  // 3-4 occupants
                                    {11000, 4300, 6350, 7850, 5500, 5500}   // 4+ occupants
                            }
                    }
            },
            // Condo/Apartment
            {
                    // Under 1000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {1680, 200, 0, 1320, 1600, 1600},  // 1 occupant
                                    {1870, 250, 0, 1550, 1850, 1850},  // 2 occupants
                                    {2170, 380, 0, 1800, 2000, 2000},  // 3-4 occupants
                                    {2400, 450, 0, 2000, 2100, 2100}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2240, 500, 0, 2100, 1800, 1800},  // 1 occupant
                                    {2430, 550, 0, 2400, 2000, 2000},  // 2 occupants
                                    {2719, 580, 0, 2800, 2300, 2300},  // 3-4 occupants
                                    {2997, 600, 0, 3200, 2400, 2400}   // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2800, 900, 0, 3000, 2800, 2800},  // 1 occupant
                                    {3000, 1100, 0, 3300, 3000, 3000},  // 2 occupants
                                    {3200, 1200, 0, 3700, 3300, 3300},  // 3-4 occupants
                                    {3500, 1300, 0, 4300, 3500, 3500}   // 4+ occupants

                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3700, 1400, 0, 3300, 3000, 3000},  // 1 occupant
                                    {4100, 1600, 0, 3700, 3100, 3100},  // 2 occupants
                                    {4600, 1700, 0, 4400, 3500, 3500},  // 3-4 occupants
                                    {5000, 1900, 0, 5200, 3900, 3900}   // 4+ occupants

                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {5000, 1900, 0, 5700, 3500, 3500},  // 1 occupant
                                    {7200, 2100, 0, 6000, 3600, 3600},  // 2 occupants
                                    {8000, 2200, 0, 6600, 3900, 3900},  // 3-4 occupants
                                    {10000, 2600, 0, 7000, 4200, 4200}   // 4+ occupants
                            }
                    },
                    // 1000-2000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2060, 300, 0, 1500, 1800, 1800},  // 1 occupant
                                    {2260, 400, 0, 1700, 2200, 2200},  // 2 occupants
                                    {2540, 500, 0, 2100, 2500, 3500},  // 3-4 occupants
                                    {3010, 620, 0, 2300, 2700, 2700}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2500, 550, 0, 3000, 2200, 2200},  // 1 occupant
                                    {2880, 670, 0, 3400, 2500, 2500},  // 2 occupants
                                    {3110, 780, 0, 3800, 2900, 2900}, // 3-4 occupants
                                    {3320, 900, 0, 4200, 3300, 3300}  // 4+ occupants

                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3100, 1250, 0, 4100, 3200, 3200},  // 1 occupant
                                    {3300, 1450, 0, 4600, 3500, 3500},  // 2 occupants
                                    {3500, 1750, 0, 5000, 3600, 3600},  // 3-4 occupants
                                    {3900, 2100, 0, 5400, 4000, 4000}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4000, 1900, 0, 4550, 3100, 3100},  // 1 occupant
                                    {4700, 2300, 0, 4950, 3300, 3300},  // 2 occupants
                                    {5200, 2700, 0, 5350, 3700, 3700},  // 3-4 occupants
                                    {5900, 3000, 0, 5650, 4100, 4100}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {5350, 2300, 0, 6000, 3900, 3900},  // 1 occupant
                                    {7550, 2500, 0, 6300, 4200, 4200},  // 2 occupants
                                    {8200, 2700, 0, 7000, 4500, 4500},  // 3-4 occupants
                                    {10500, 3100, 0, 7400, 4700, 4700}   // 4+ occupants
                            }
                    },
                    // 2000+ square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2440, 350, 0, 1800, 2300, 2300},  // 1 occupant
                                    {2727, 570, 0, 2100, 2600, 2600}, // 2 occupants
                                    {3010, 900, 0, 2450, 2900, 2900}, // 3-4 occupants
                                    {3577, 980, 0, 2600, 3300, 3300}  // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2700, 610, 0, 3650, 2600, 2600},  // 1 occupant
                                    {3100, 690, 0, 4050, 2900, 2900}, // 2 occupants
                                    {3300, 820, 0, 4650, 3300, 3300}, // 3-4 occupants
                                    {3600, 980, 0, 5150, 3600, 3600}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3670, 1500, 0, 4500, 3500, 3500},  // 1 occupant
                                    {3870, 1700, 0, 5000, 3700, 3700}, // 2 occupants
                                    {4100, 2000, 0, 5400, 4200, 4200}, // 3-4 occupants
                                    {4300, 2350, 0, 5700, 4600, 4600}  // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4250, 2150, 0, 5000, 3530, 3530},  // 1 occupant
                                    {5050, 2550, 0, 5300, 3730, 3730}, // 2 occupants
                                    {5400, 2850, 0, 5600, 4200, 4200}, // 3-4 occupants
                                    {6200, 3150, 0, 6000, 4630, 4630}  // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {6000, 2600, 0, 6500, 4100, 4100},  // 1 occupant
                                    {7800, 3100, 0, 6800, 4400, 4400},  // 2 occupants
                                    {8500, 3600, 0, 7400, 4900, 4900},  // 3-4 occupants
                                    {11100, 4000, 0, 7800, 5100, 5100}   // 4+ occupants
                            }
                    }
            },
            //Other
            {
                    // Under 1000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {1971, 300, 2400, 1500, 2000, 2000},  // 1 occupant
                                    {2160, 410, 2523, 1850, 2250, 2250},  // 2 occupants
                                    {2500, 500, 2600, 2100, 2500, 2500},  // 3-4 occupants
                                    {2800, 550, 2720, 2500, 2600, 2600}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2800, 500, 2800, 2500, 2800, 2800},  // 1 occupant
                                    {2910, 550, 3100, 2800, 3000, 3000},  // 2 occupants
                                    {3000, 580, 3250, 3400, 3300, 3300},  // 3-4 occupants
                                    {3200, 600, 3500, 3800, 3400, 3400}   // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3000, 1000, 3600, 3000, 3000, 3000},  // 1 occupant
                                    {3210, 1250, 3740, 3500, 3300, 3300},  // 2 occupants
                                    {3500, 1320, 3900, 4100, 3520, 3520},  // 3-4 occupants
                                    {3800, 1420, 4050, 5000, 3800, 3800}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4000, 1600, 4500, 3700, 3330, 3330},  // 1 occupant
                                    {5500, 1750, 4600, 4500, 3500, 3500},  // 2 occupants
                                    {6200, 1900, 4800, 5200, 3720, 3720},  // 3-4 occupants
                                    {8000, 2000, 5100, 5800, 4000, 4000}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {8000, 2000, 5000, 5800, 3500, 3500},  // 1 occupant
                                    {9500, 2100, 5200, 6800, 3700, 3700},  // 2 occupants
                                    {10200, 2300, 5300, 7200, 4000, 4000},  // 3-4 occupants
                                    {12000, 3000, 5600, 7800, 4300, 4300}   // 4+ occupants
                            }
                    },
                    // 1000-2000 square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2443, 300, 2590, 3170, 1400, 1400},  // 1 occupant
                                    {2750, 380, 2620, 3770, 1560, 1560},  // 2 occupants
                                    {3111, 500, 2730, 4670, 1900, 1900},  // 3-4 occupants
                                    {3580, 590, 2800, 5570, 2200, 2200}   // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4000, 550, 3800, 5600, 2400, 2400},  // 1 occupant
                                    {5000, 700, 4320, 5940, 2600, 2600},  // 2 occupants
                                    {6500, 950, 4800, 6140, 3300, 3300}, // 3-4 occupants
                                    {7300, 1100, 5500, 6340, 3800, 3800}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4300, 1200, 5000, 6000, 4000, 4000},  // 1 occupant
                                    {5500, 1350, 5800, 6200, 4310, 4310},  // 2 occupants
                                    {6800, 1520, 6200, 6420, 4600, 4600},  // 3-4 occupants
                                    {8340, 1700, 6100, 6500, 5100, 5100}   // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {4800, 1700, 5350, 3680, 3800, 3800},  // 1 occupant
                                    {6300, 1900, 5500, 4280, 3800, 3800},  // 2 occupants
                                    {8500, 2150, 5720, 5180, 4220, 4220},  // 3-4 occupants
                                    {9000, 2400, 5900, 6080, 4400, 4400}   // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {9500, 2500, 5370, 6000, 4000, 4000},  // 1 occupant
                                    {10100, 2780, 5500, 6600, 4640, 4640},  // 2 occupants
                                    {11200, 3000, 5800, 6800, 5000, 5000},  // 3-4 occupants
                                    {14000, 3500, 6200, 7400, 5430, 5430}   // 4+ occupants
                            }
                    },
                    // 2000+ square feet
                    {
                            // Monthly bill under $50
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {2822, 300, 2810, 3340, 3800, 3800},  // 1 occupant
                                    {3010, 560, 3000, 3940, 4070, 4070}, // 2 occupants
                                    {3300, 890, 3468, 4840, 5000, 5000}, // 3-4 occupants
                                    {3600, 1000, 3760, 5740, 5600, 5600}  // 4+ occupants
                            },
                            // Monthly bill $50-$100
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {3600, 1200, 4300, 5900, 3500, 3500},  // 1 occupant
                                    {3840, 1380, 4900, 6330, 3930, 3930}, // 2 occupants
                                    {3900, 1600, 5320, 6440, 4360, 4360}, // 3-4 occupants
                                    {5100, 1750, 5800, 6900, 5000, 5000}  // 4+ occupants
                            },
                            // Monthly bill $100-$150
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {5000, 1800, 5300, 3510, 4100, 4100},  // 1 occupant
                                    {6200, 2000, 5690, 4110, 4500, 4500}, // 2 occupants
                                    {7000, 2200, 6250, 5010, 4780, 4780}, // 3-4 occupants
                                    {8000, 2300, 6500, 5910, 5360, 5360}  // 4+ occupants
                            },
                            // Monthly bill $150-$200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {8000, 2400, 5440, 3800, 4200, 4200},  // 1 occupant
                                    {8300, 2500, 5600, 4500, 4640, 4640}, // 2 occupants
                                    {9000, 2650, 5800, 5380, 5000, 5000}, // 3-4 occupants
                                    {9500, 2800, 6000, 6200, 5400, 5400}  // 4+ occupants
                            },
                            // Monthly bill over $200
                            {
                                    //natural gas, electricity, oil, propane, wood
                                    {9500, 2300, 5670, 6200, 4300, 4300},  // 1 occupant
                                    {1010, 3000, 5800, 6900, 4700, 4700},  // 2 occupants
                                    {10300, 3800, 6100, 7500, 5100, 5100},  // 3-4 occupants
                                    {11000, 4300, 6350, 7850, 5500, 5500}   // 4+ occupants
                            }
                    }
            }
    };

    //Consumption Data
    final public static double[][] clothesEmission = {
            //monthly, quarterly, annually, rarely
            {180, 60, 50, 2.5},//regularly
            {252, 84, 70, 3.5},//occasionally
            {360, 120, 100, 5}//no
    };
    final public static double[] electronicEmission = {0, 300, 600, 900, 1200};
    final public static double[][] recyclingClothesEmission = {
            //monthly, quarterly, annually, rarely
            {0, 0, 0, 0}, //never
            {-54, -18, -15, -0.75}, //occasionally
            {-108, -36. -30, -1.5}, //frequently
            {-180, -60, -50, -2.5} //always
    };
    final public static double[][] recyclingElectronicEmission = {
            //1, 2, 3, 4+
            {0, 0, 0, 0}, //never
            {-45, -60, -90, -120}, //occasionally
            {-60, -120. -180, -240}, //frequently
            {-90, -180, -270, -360} //always
    };
    final public static double[] globalAverages = {
            0.29536375, 0.99422127, 1.7432004, 3.9272263, 4.6171236, 0.45155162, 8.752724,
            6.4218745, 4.2378173, 2.3045583, 8.133404, 4.611434, 4.017375, 14.985412, 6.8781943,
            3.6746833, 5.1708703, 25.672274, 0.5964455, 4.3772573, 6.1669006, 7.6875386, 1.7894346,
            0.631487, 6.9370627, 1.3489918, 1.7583066, 4.083284, 6.1034565, 2.838951, 2.2454574,
            5.0039577, 23.950201, 6.8044534, 0.26295447, 0.06194545, 1.1900775, 0.34292704,
            14.249212, 0.9588915, 0.040548485, 0.13367727, 4.3041654, 7.992761, 1.9223082,
            0.49327007, 1.2447897, 3.9950094, 1.5226681, 0.41668788, 4.348515, 1.8659163, 9.189007,
            5.616782, 9.3357525, 0.036375992, 4.940161, 0.40418932, 2.1058853, 2.1051137,
            0.49869007, 2.3117273, 2.333106, 1.2174718, 3.0307202, 0.18914719, 7.77628, 1.0527312,
            0.15458965, 6.8578663, 7.886797, 8.817789, 6.1743994, 5.983708, 14.084624, 1.1550449,
            6.5267396, 4.603891, 2.8509297, 2.3882635, 0.2847278, 2.962545, 7.9837584, 0.6215505,
            5.7451057, 10.473997, 2.7133646, 1.0756185, 0.35742033, 0.15518051, 4.3736935,
            0.21119381, 10.132565, 1.0696708, 4.081913, 4.449911, 9.499798, 1.9966822, 2.6456614,
            7.7993317, 4.024638, 7.7211185, 6.208912, 5.726825, 2.2945588, 8.501681, 2.0301995,
            13.979704, 0.45998666, 0.5184742, 4.830646, 25.578102, 1.4251612, 3.0803475, 3.561689,
            4.3543963, 1.3594668, 0.1653753, 9.242238, 3.8097827, 4.606163, 0.28005043, 1.777996,
            11.618432, 1.5127679, 0.14871116, 0.10262384, 8.576508, 3.2475724, 0.31153768,
            3.1035979, 3.6353714, 0.957337, 3.2697906, 4.0153365, 1.3243006, 1.6565942, 11.150772,
            3.6558185, 4.8447766, 1.8263615, 0.24274588, 0.6445672, 1.5399038, 4.1700416, 0.5074035,
            7.1372175, 17.641167, 6.212154, 0.79879653, 0.116688, 0.5891771, 3.8729508, 10.5346775,
            4.741475, 1.9513915, 3.6245701, 7.5093055, 9.85179, 15.730261, 0.84893465, 12.123921,
            0.6660658, 2.699258, 0.77131313, 1.3299496, 1.7891879, 1.3014648, 8.106886, 4.050785,
            37.601273, 3.739777, 11.416899, 0.112346195, 3.2986484, 4.708081, 2.6149206, 10.293288,
            2.2964725, 1.1218625, 0.5816142, 18.197495, 0.6738352, 6.024712, 6.1495123, 0.13124847,
            8.911513, 14.352394, 6.051555, 5.9979916, 0.41232163, 0.03676208, 6.7461643, 2.4865332,
            11.598764, 0.1680176, 5.1644425, 0.7936504, 0.4696261, 5.8029985, 3.6069093, 4.0478554,
            1.2490375, 11.630868, 1.0064901, 0.23771806, 3.7762568, 0.2910665, 1.7686282, 22.423758,
            2.879285, 5.1052055, 11.03418, 7.636793, 1.0004411, 0.12744623, 3.5578535, 25.833244,
            4.7201805, 14.949616, 6.2268133, 2.3060381, 3.4830604, 0.6363055, 2.7168686, 3.4995174,
            2.2819076, 4.658219, 0.33701748, 0.44570068, 0.542628
    };

    //ALL OPTIONS
    // Travel related options
    final public static String[] carTypes = { "Gasoline",
                                        "Diesel",
                                        "Hybrid",
                                        "Electric",
                                        "I don't know" };
    final public static String[] annualKmDriven = {"Up to 5,000 km (3,000 miles)",
                                            "5,000–10,000 km (3,000–6,000 miles)",
                                            "10,000–15,000 km (6,000–9,000 miles)",
                                            "15,000–20,000 km (9,000–12,000 miles)",
                                            "20,000–25,000 km (12,000–15,000 miles)",
                                            "More than 25,000 km (15,000 miles)"};
    final public static String[] publicTransportFrequency = {"Never",
                                                        "Occasionally (1-2 times/week)",
                                                        "Frequently (3-4 times/week)",
                                                        "Always (5+ times/week)"};
    final public static String[] publicTransportTime = {"Under 1 hour",
                                                    "1-3 hours",
                                                    "3-5 hours",
                                                    "5-10 hours",
                                                    "More than 10 hours"};
    final public static String[] shortHaulFlights = {"None",
                                                "1-2 flights",
                                                "3-5 flights",
                                                "6-10 flights",
                                                "More than 10 flights"};
    final public static String[] longHaulFlights = {"None",
                                                "1-2 flights",
                                                "3-5 flights",
                                                "6-10 flights",
                                                "More than 10 flights"};

    // Diet related options
    final  public static String[] dietTypes = {"Vegetarian",
                                        "Vegan",
                                        "Pescatarian (fish/seafood)",
                                        "Meat-based (eat all types of animal products)"};
    final public static String[] beefFrequency = { "Daily",
                                            "Frequently (3-5 times/week)",
                                            "Occasionally (1-2 times/week)",
                                            "Never" };
    final public static String[] porkFrequency = { "Daily",
                                                "Frequently (3-5 times/week)",
                                                "Occasionally (1-2 times/week)",
                                                "Never" };
    final public static String[] chickenFrequency = { "Daily",
                                                "Frequently (3-5 times/week)",
                                                "Occasionally (1-2 times/week)",
                                                "Never" };
    final public static String[] fishSeafoodFrequency = { "Daily",
                                                    "Frequently (3-5 times/week)",
                                                    "Occasionally (1-2 times/week)",
                                                    "Never" };
    final public static String[][] meatFrequencies = {beefFrequency,
                                                porkFrequency,
                                                chickenFrequency,
                                                fishSeafoodFrequency};
    final public static String[] foodWasteFrequency = { "Never",
                                                    "Rarely",
                                                    "Occasionally",
                                                    "Frequently" };

    // Residence related options
    final public static String[] homeTypes = { "Detached house",
                                            "Semi-detached house",
                                            "Townhouse",
                                            "Condo/Apartment",
                                            "Other" };
    final public static String[] householdSizes = {"1", "2", "3-4", "5 or more"};
    final public static String[] housingSizes = {"Under 1000 sq. ft.",
                                            "1000-2000 sq. ft.", "Over 2000 sq. ft."};
    final public static String[] heatingTypes = { "Natural Gas",
                                            "Electricity",
                                            "Oil",
                                            "Propane",
                                            "Wood",
                                            "Other" };
    final public static String[] electricityBills = { "Under $50",
                                                "$50-$100",
                                                "$100-$150",
                                                "$150-$200",
                                                "Over $200" };
    final public static String[] renewableEnergyUsage = { "Yes, primarily",
                                                    "Yes, partially",
                                                    "No" };

    // Consumption related options
    final public static String[] clothingPurchaseFrequency = { "Monthly",
                                                            "Quarterly",
                                                            "Annually",
                                                            "Rarely" };
    final public static String[] secondHandPurchaseFrequency = { "Yes, regularly",
                                                            "Yes, occasionally",
                                                            "No" };
    final public static String[] electronicDevicesPurchased = { "None", "1", "2", "3 or more" };
    final public static String[] recyclingFrequency = { "Never", "Occasionally", "Frequently", "Always" };

    // Array of supported countries
    final public static String[] countries = {
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
            "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde",
            "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros",
            "Congo (Congo-Brazzaville)", "Congo (Congo-Kinshasa)", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
            "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt",
            "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France",
            "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau",
            "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
            "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, North",
            "Korea, South", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
            "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
            "Montenegro", "Morocco", "Mozambique", "Myanmar (formerly Burma)", "Namibia", "Nauru", "Nepal", "Netherlands",
            "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau",
            "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar",
            "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
            "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles",
            "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
            "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan",
            "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia",
            "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
            "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
    };
}
