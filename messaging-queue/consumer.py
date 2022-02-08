from typing import List
import fire
import asyncio
import aiohttp


class Consumer:
    def __init__(self, address, exchange, queues) -> None:
        self.__address = address
        self.__exchange = exchange
        self.__queues = self.__init_queues(queues)
        pass

    def __init_queues(self, queues) -> List[str]:
        assert isinstance(queues, tuple) or isinstance(queues, list)
        return list(queues)
    
    async def monitor(self):
        async with aiohttp.ClientSession() as session:
            while 1:
                for q in self.__queues:
                    url = f"http://{self.__address}/exchange/{self.__exchange}/queue/{q}"
                    try:
                        async with session.get(url) as resp:
                            assert resp.status == 200
                            mesg = await resp.json()
                            print(f"from queue={q} mesg={mesg}")
                    except Exception as e:
                        await asyncio.sleep(1)


def runner(address="localhost:8080", exchange="TEST", queues=""):
    c = Consumer(address, exchange, queues)
    asyncio.run(c.monitor())

if __name__ == "__main__":
    fire.Fire(runner)

