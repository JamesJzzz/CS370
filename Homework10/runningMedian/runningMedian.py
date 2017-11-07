# Zhiyuan (James) Zhang, Francesca Bueti, Alexander Vallorosi
# I pledge my honor that I have abided by the Stevens Honors System

import heapq	#add heap functionality to python lists
minHeap, maxHeap = [], []

#returns median given two heaps
def getMedian(a,b):
	if len(a)>len(b): return  a[0]
	elif len(b)>len(a): return  -1*b[0]
	else: return float(a[0]+(-1*b[0]))/2	#average of two middle numbers

for i in range(0, int(input())):	#read in input
	currNum = int(input())
	# adds first number to maxHeap
	if len(maxHeap) == 0:
		heapq.heappush(maxHeap, currNum)
		print(currNum)
	
	# adds second number
	elif len(minHeap) == 0:
		# add to minHeap if num is less than max heap
		if currNum<maxHeap[0]:
			heapq.heappush(minHeap,-1*currNum)
			print(getMedian(maxHeap, minHeap))
		# else add to maxHeap and balance by adding first num to minHeap		
		else:
			heapq.heappush(minHeap,-1*heapq.heappop(maxHeap))
			heapq.heappush(maxHeap,currNum)
			print(getMedian(maxHeap,minHeap))
	else:
		# add number to the correct heap
		if -1*currNum > minHeap[0]:
				heapq.heappush(minHeap,-1*currNum)
		else:
			heapq.heappush(maxHeap,currNum)
		
		# balance heaps if they're unbalanced
		if(len(maxHeap)-len(minHeap)>1):
			heapq.heappush(minHeap, -1*heapq.heappop(maxHeap))
		elif(len(minHeap)-len(maxHeap)>1):
			heapq.heappush(maxHeap, -1*heapq.heappop(minHeap))

		print(getMedian(maxHeap, minHeap))
