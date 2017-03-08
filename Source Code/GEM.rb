# GEM class 
class GEM  

    @m = 2147483647
    @a = 48271;
    @q = 44488;
    @r = 3399;
    @r_seed = 12345678;

    # Constructor 
    def initialize()  
    end  

    def gemGenerator()
        #esto se hace para trucar el overflow de multiplicar numeros tan grandes
        hi = r_seed / @q
        lo = r_seed - @q * hi
        t = @a * lo - r * hi
            if t > 0
                @r_seed = t
            else
                @r_seed = t + m
            end
        numeroGenerado = @r_seed / @m
        return numeroGenerado
    end

    def randomGeneratorRuby()
        number = rand()
        return number
    end
 

end )